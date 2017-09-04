package com.opensdk.eleme2.utils.json;

public class StdoutStreamErrorListener extends BufferErrorListener {

    public void end() {
        System.out.print(buffer.toString());
    }
}
