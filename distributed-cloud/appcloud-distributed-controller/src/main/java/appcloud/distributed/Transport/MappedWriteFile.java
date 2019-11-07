package appcloud.distributed.Transport;

import appcloud.distributed.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lizhenhao on 2017/12/17.
 */
public class MappedWriteFile {

    protected final static Logger LOGGER = LoggerFactory.getLogger(MappedWriteFile.class);


    public static MappedWriteFile createMappedFile(int capacity, String filePath) throws IOException {
        return createMappedFile(capacity, filePath, 0);
    }

    public static MappedWriteFile createMappedFile(int capacity, String filePath, long position) throws IOException {
        MappedWriteFile mappedFile = new MappedWriteFile(capacity, filePath, position);
        mappedFile.init();
        return mappedFile;
    }

    private int capacity;
    private String filePath;
    private long position;
    private ByteBuffer directByteBuffer;
    private FileChannel fileChannel;


    public MappedWriteFile(int capacity, String filePath, long position) {
        this.capacity = capacity;
        this.filePath = filePath;
        this.position = position;
    }

    public MappedWriteFile(int capacity, String filePath) {
        this(capacity, filePath, 0);
    }

    private void init() throws IOException {
        directByteBuffer = ByteBuffer.allocateDirect(this.capacity);
        fileChannel = new RandomAccessFile(filePath, "rw").getChannel();
        fileChannel.position(this.position);
    }

    public void write(byte[] bytes, long writePos) throws IOException {
        directByteBuffer.put(bytes);
        directByteBuffer.flip();
        if (writePos != position) {
            fileChannel.position(writePos);
        }

        while (directByteBuffer.hasRemaining()) {
            fileChannel.write(directByteBuffer);
        }
        directByteBuffer.clear();

    }

    public void write(byte[] bytes) throws IOException {
        directByteBuffer.put(bytes);
        directByteBuffer.flip();

        while (directByteBuffer.hasRemaining()) {
            fileChannel.write(directByteBuffer);
        }
        directByteBuffer.clear();

    }

    public void close() {
        try {
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void force() throws IOException {
        fileChannel.force(true);
    }

    public void destroyMappedFile() throws IOException {
        if (fileChannel != null) {
            fileChannel.close();
        }
        FileUtil.clean(directByteBuffer);
        LOGGER.info("destroy file success!");
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public FileChannel getFileChannel() {
        return fileChannel;
    }

    public void setFileChannel(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }
}
