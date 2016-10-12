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

        try {
            InputStream in = getStockfishResource();
            File localStockfish = getTempStockfish();

            try (OutputStream out = new FileOutputStream(localStockfish)) {
                byte[] buf = new byte[8192];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }

            _engineCommand = localStockfish.getAbsolutePath();
            return _engineCommand;

        } catch (IOException e) {
            throw new UCIEngineException(e);
        }
    }

    private InputStream getStockfishResource() {
        String stockfishResource;

        if (OSUtils.isWindows()) {
            stockfishResource = "/engines/windows/stockfish_7_x64.exe";
        } else if (OSUtils.isLinux()) {
            stockfishResource = "/engines/linux/stockfish_7_x64";
        } else if (OSUtils.isMac()) {
            stockfishResource = "/engines/mac/stockfish-7-64";
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }
        return getClass().getResourceAsStream(stockfishResource);
    }

    private File getTempStockfish() throws IOException {
        File tmpStockfish = File.createTempFile("stockfish-", ".exe");
        tmpStockfish.setExecutable(true, false);
        tmpStockfish.deleteOnExit();
        return tmpStockfish;
    }
}
