

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class niotest {
    public niotest(String filename) {
        //save
        try {
            FileChannel fileChannel = new FileOutputStream(filename).getChannel();

            ByteBuffer bBuffer = ByteBuffer.allocate(255);
            bBuffer.clear();
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            //fileChannel.read(bBuffer);

            //get the name
            bBuffer.putInt(12);
            bBuffer.putInt(13);

            bBuffer.flip();

            fileChannel.write(bBuffer);

            bBuffer.clear();
            bBuffer.putInt(100);
            bBuffer.flip();

            fileChannel.write(bBuffer);

            fileChannel.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //load

        try {
            FileChannel fileChannel = new FileInputStream(filename).getChannel();

            ByteBuffer bBuffer = ByteBuffer.allocate(255);
            bBuffer.clear();
            bBuffer.order(ByteOrder.LITTLE_ENDIAN);

            System.out.println(fileChannel.read(bBuffer));
            bBuffer.rewind();
            //get the name
            int width = bBuffer.getInt();
            int height = bBuffer.getInt();
            //bBuffer.rewind();
            int ta = bBuffer.getInt();

            System.out.println("Width: " + width + " Height: " + height + " " + ta);

            fileChannel.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
