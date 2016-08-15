package magnus4j.engine;

import magnus4j.engine.uci.AbstractUCIEngine;
import magnus4j.engine.uci.UCIEngineException;
import magnus4j.utils.OSUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stockfish internal engine.
 */
public class StockfishEngine extends AbstractUCIEngine {

    private String _engineCommand;

    @Override
    public String getEngineName() {
        return "Stockfish 7";
    }

    @Override
    public String getEngineCommand() {

        if (_engineCommand != null)
            return _engineCommand;

        String stockfishResource;

        if (OSUtils.isWindows()) {
            stockfishResource = "/engines/windows/stockfish_7_x64.exe";
        } else {
            stockfishResource = "/engines/linux/stockfish_7_x64";
        }

        try {
            InputStream in = getClass().getResourceAsStream(stockfishResource);
            File tmpStockfish = File.createTempFile("stockfish-6-", ".exe");
            OutputStream out = new FileOutputStream(tmpStockfish);

            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            out.close();

            tmpStockfish.setExecutable(true, false);

            _engineCommand = tmpStockfish.getAbsolutePath();
            return _engineCommand;

        } catch (IOException e) {
            throw new UCIEngineException(e);
        }
    }
}
