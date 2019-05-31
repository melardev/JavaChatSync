package com.melardev.chat.net.packets;


public class PacketFile extends PacketChat {
    public enum PacketFileType {
        IMAGE, VIDEO, AUDIO, EXECUTABLE, TEXT, PDF, RAW
    }

    private byte[] fileContent;
    private PacketFileType filetype;
    private String fileName;
    private String extension;

    public String getExtension() {
        return extension;
    }

    public PacketFileType getFiletype() {
        return filetype;
    }

    public PacketFile(PacketFileType _fileType, byte[] _content, String extension, String _dst) {
        super(PacketType.FILE_ATTACH, _dst);
        filetype = _fileType;
        fileContent = _content;
        this.extension = extension;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public byte[] getFileContent() {
        // TODO Auto-generated method stub
        return fileContent;
    }

    public String getFileName() {
        return fileName;
    }

}
