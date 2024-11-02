import java.io.*;

public class AudioSteganographyDecoder {

    // Trích xuất tin nhắn từ tệp âm thanh
    public static String extractMessage(String audioFilePath, int messageLength) throws Exception {
        File inputFile = new File(audioFilePath);
        byte[] audioBytes = readFile(inputFile);

        byte[] messageBytes = new byte[messageLength];
        
        // Giải mã từng bit của tin nhắn từ các bit ít quan trọng của dữ liệu âm thanh
        for (int i = 0; i < messageLength; i++) {
            for (int bit = 0; bit < 8; bit++) {
                // Lấy giá trị của bit ít quan trọng từ byte dữ liệu âm thanh
                int bitValue = (audioBytes[i * 8 + bit] & 1) << (7 - bit);
                messageBytes[i] = (byte) (messageBytes[i] | bitValue);
            }
        }

        return new String(messageBytes);
    }

    // Đọc nội dung từ file
    public static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    public static void main(String[] args) {
        try {
            String audioFilePath = "output.wav"; // Tệp âm thanh chứa tin nhắn
            int messageLength = 14; // Chiều dài tin nhắn bí mật (số byte)

            String message = extractMessage(audioFilePath, messageLength);
            System.out.println("Extracted message: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
