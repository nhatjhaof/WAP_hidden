import java.io.*;
public class AudioSteganography {
	 // Chèn tin nhắn vào các bit ít quan trọng của dữ liệu âm thanh
    public static void embedMessage(String audioFilePath, String message, String outputFilePath) throws Exception {
        File inputFile = new File(audioFilePath);
        File outputFile = new File(outputFilePath);
        
        byte[] audioBytes = readFile(inputFile);
        byte[] messageBytes = message.getBytes();
        
        // Đảm bảo tin nhắn không quá lớn so với tệp âm thanh
        if (messageBytes.length * 8 > audioBytes.length) {
            throw new Exception("Message is too long to embed in this audio file.");
        }

        // Giấu từng bit của tin nhắn vào các bit ít quan trọng của dữ liệu âm thanh
        for (int i = 0; i < messageBytes.length; i++) {
            for (int bit = 0; bit < 8; bit++) {
                // Nhận giá trị của bit hiện tại từ tin nhắn
                int bitValue = (messageBytes[i] >> (7 - bit)) & 1;
                
                // Thay thế bit cuối cùng của byte dữ liệu âm thanh bằng bit tin nhắn
                audioBytes[i * 8 + bit] = (byte) ((audioBytes[i * 8 + bit] & 0xFE) | bitValue);
            }
        }
        
        writeFile(outputFile, audioBytes);
        System.out.println("Message embedded successfully!");
    }

    // Đọc nội dung từ file
    public static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    // Ghi nội dung vào file
    public static void writeFile(File file, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }

    public static void main(String[] args) {
        try {
            // Đường dẫn đến tệp âm thanh gốc và tệp đích
            String audioFilePath = "input.wav"; // Tệp âm thanh gốc
            String message = "Secret message";  // Tin nhắn bí mật cần giấu
            String outputFilePath = "output.wav"; // Tệp đầu ra với tin nhắn được giấu

            embedMessage(audioFilePath, message, outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

