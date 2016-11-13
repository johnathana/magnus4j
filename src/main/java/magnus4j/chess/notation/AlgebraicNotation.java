package magnus4j.chess.notation;

import magnus4j.chess.Piece;
import magnus4j.chess.PieceType;
import magnus4j.chess.Side;
import magnus4j.chess.Square;
import magnus4j.chess.game.Line;
import magnus4j.chess.logic.PieceMoves;
import magnus4j.chess.logic.PieceUtils;
import magnus4j.chess.logic.PositionUtils;
import magnus4j.chess.move.Move;
import magnus4j.chess.position.MovablePosition;
import magnus4j.chess.position.Position;

import java.util.List;
import java.util.Map;

/**
 * Algebraic notation.
 */
class AlgebraicNotation implements Notation {

    private static final String KINGSIDE_CASTLING = "O-O";

    private static final String QUEENSIDE_CASTLING = "O-O-O";

    private Map<Piece, String> _pieceMap;

    /**
     * Default constructor
     * 
     * @param pieceMap
     *            the piece map.
     */
    AlgebraicNotation(Map<Piece, String> pieceMap) {
        _pieceMap = pieceMap;
    }

    @Override
    public String moveToString(final Move move, final Position pos) {

        boolean isCapture = false;

        Piece piece = pos.getPiece(move.getFrom());
        Piece capturePiece = pos.getPiece(move.getTo());

        if (piece == null) {
            System.out.format("BUG: Move: %s in position %s%n", move, pos.getFEN());
            throw new IllegalArgumentException();
        }

        if (capturePiece != null) {
            isCapture = true;
        }

        // Castle handling
        if (piece.getPieceType() == PieceType.KING) {

            if (PieceUtils.isWhiteShortCastle(piece, move)) {
                return KINGSIDE_CASTLING;
            }

            if (PieceUtils.isWhiteLongCastle(piece, move)) {
                return QUEENSIDE_CASTLING;
            }

            if (PieceUtils.isBlackShortCastle(piece, move)) {
                return KINGSIDE_CASTLING;
            }

            if (PieceUtils.isBlackLongCastle(piece, move)) {
                return QUEENSIDE_CASTLING;
            }
        }

        // Pawn handling
        if (piece.getPieceType() == PieceType.PAWN) {
            String pawnMove = move.getTo().toLowerCase();

            if (move.getTo() == pos.getEnPassant()) {
                isCapture = true;
            }

            if (isCapture) {
                pawnMove = Character.toLowerCase(move.getFrom().getFile()) + "x" + move.getTo().toLowerCase();
            }

            if (move.isPromotion()) {
                pawnMove += promotionNotation(move.getPromotionType());
            }

            return pawnMove;
        }

        StringBuilder sb = new StringBuilder(_pieceMap.get(piece));
        appendRankOrFile(move, pos, sb);

        if (isCapture) {
            sb.append("x");
        }

        sb.append(move.getTo().toLowerCase());

        return sb.toString();
    }

    @Override
    public String lineToString(final Line line) {

        StringBuilder sb = new StringBuilder();
        MovablePosition curPos = new MovablePosition(line.getPosition());

        if (!curPos.isWhitesTurn()) {
            sb.append(curPos.getFullMoveNumber()).append("... ");
        }

        for (Move move : line) {
            String notation = moveToString(move, curPos);
            if (curPos.isWhitesTurn()) {
                sb.append(curPos.getFullMoveNumber()).append(".");
            }
            sb.append(notation);

            curPos.makeMove(move);
            kingInCheckOrMate(curPos, sb);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public Move stringToMove(String strMove, final Position position) {

        if (strMove.equals(KINGSIDE_CASTLING)) {
            switch (position.getActiveSide()) {
            case WHITE:
                return new Move(Square.E1, Square.G1);
            case BLACK:
                return new Move(Square.E8, Square.G8);
            }
        }

        if (strMove.equals(QUEENSIDE_CASTLING)) {
            switch (position.getActiveSide()) {
            case WHITE:
                return new Move(Square.E1, Square.C1);
            case BLACK:
                return new Move(Square.E8, Square.C8);
            }
        }

        strMove = strMove.replaceAll("[\\+#]", "");

        /*
         * Promotion.
         */
        PieceType promotion = null;
        if (strMove.contains("=")) {
            String[] promotionArray = strMove.split("=");
            strMove = promotionArray[0];
            promotion = pieceFromChar(promotionArray[1].charAt(0));
        }

        Piece piece = getPiece(strMove, position.getActiveSide());
        Square to = getToSquare(strMove);
        Square from = getFromSquare(strMove, piece, to, position);

        return new Move(from, to, promotion);
    }

    private static Square getToSquare(String strMove) {
        String toMove = strMove.substring(strMove.length() - 2, strMove.length());
        return Square.fromName(toMove);
    }

    private static Square getFromSquare(String strMove, Piece piece, Square to, Position position) {
        Square from;
        List<Square> fromSquares = PositionUtils.piecesWithTargetSquare(piece, to, position);

        if (fromSquares.size() == 1) {
            from = fromSquares.get(0);
        } else {
            from = getMultiTargetSquare(strMove, piece, fromSquares);
        }
        return from;
    }

    private static Square getMultiTargetSquare(String strMove, Piece piece,
            List<Square> fromSquares) {

        Square from = null;
        char he = strMove.charAt(piece.getPieceType() == PieceType.PAWN ? 0 : 1);

        if (Character.isDigit(he)) {
            // Rank filter
            for (Square square : fromSquares) {
                if (square.getRank() == he) {
                    from = square;
                    break;
                }
            }
        } else {
            // File filter
            for (Square square : fromSquares) {
                if (square.getFile() == Character.toUpperCase(he)) {
                    from = square;
                    break;
                }
            }
        }
        return from;
    }

    private static Piece getPiece(String strMove, Side side) {
        char pieceChar = strMove.charAt(0);
        PieceType pieceType = PieceType.PAWN;

        if (Character.isUpperCase(pieceChar)) {
            pieceType = pieceFromChar(pieceChar);
        }
        return PieceUtils.getPieceFromType(pieceType, side);
    }

    private void appendRankOrFile(Move move, Position pos, StringBuilder sb) {

        Piece piece = pos.getPiece(move.getFrom());
        if (piece.getPieceType() != PieceType.ROOK &&
            piece.getPieceType() != PieceType.KNIGHT &&
            piece.getPieceType() != PieceType.QUEEN) {
            return;
        }

        List<Square> squares = PositionUtils.getSquaresWithPiece(piece, pos);
        squares.remove(move.getFrom());

        if (squares.isEmpty()) {
            return;
        }

        int samePieceTarget = 0;
        int samePieceSameRank = 0;
        int samePieceSameFile = 0;

        for (Square square : squares) {

            if (PieceMoves.getMoveSquares(pos, square).contains(move.getTo())) {

                samePieceTarget++;

                if (square.getRank() == move.getFrom().getRank())
                    samePieceSameRank++;

                if (square.getFile() == move.getFrom().getFile())
                    samePieceSameFile++;
            }
        }

        if (samePieceTarget > 0) {

            if (samePieceSameRank > 0)
                sb.append(Character.toLowerCase(move.getFrom().getFile()));

            if (samePieceSameFile > 0)
                sb.append(move.getFrom().getRank());

            if (samePieceSameRank == 0 && samePieceSameFile == 0) {
                sb.append(Character.toLowerCase(move.getFrom().getFile()));
            }
        }
    }

    private void kingInCheckOrMate(Position position, StringBuilder sb) {
        if (PositionUtils.isKingInCheck(position)) {
            if (PositionUtils.isMate(position)) {
                sb.append("#");
            } else {
                sb.append("+");
            }
        }
    }

    private static String promotionNotation(final PieceType pieceType) {
        switch (pieceType) {
        case QUEEN:
            return "=Q";
        case ROOK:
            return "=R";
        case BISHOP:
            return "=B";
        case KNIGHT:
            return "=N";
        default:
            throw new IllegalArgumentException();
        }
    }

    private static PieceType pieceFromChar(char pieceChar) {
        switch (pieceChar) {
        case 'N':
            return PieceType.KNIGHT;
        case 'B':
            return PieceType.BISHOP;
        case 'R':
            return PieceType.ROOK;
        case 'Q':
            return PieceType.QUEEN;
        case 'K':
            return PieceType.KING;
        default:
            throw new IllegalArgumentException();
        }
    }
}
