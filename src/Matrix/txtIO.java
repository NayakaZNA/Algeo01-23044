package Matrix;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class txtIO {
    public static MatrixADT readTXT(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            // Menentukan dimensi matriks
            int i = 0, m = 0, n = 0;
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                m++;
                if (m == 1) {
                    n = line.split(" ").length;
                }
            }

            // Membuat matriks
            MatrixADT inputMatrix = new MatrixADT(m, n);
            br.close();

            // Mengisi matriks
            br = new BufferedReader(new FileReader(filename));
            i = 0;
            line = br.readLine();
            while (line != null) {
                String[] temp = line.split(" ");
                // Mengisi baris ke-i
                for (int j = 0; j < temp.length; j++) {
                    inputMatrix.setElmt(i, j, Double.valueOf(temp[j]));
                }
                line = br.readLine();
                i++;
            }
            br.close();
            initWriter(filename);
            writeTXT(filename, inputMatrix);
            return inputMatrix;
                
        } catch (IOException e) {
            // Jika file kosong atau tidak ada, kembalikan pesan error dan null
            System.out.println("File kosong atau tidak ada");
            e.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("unused")
    public static void initWriter(String filename) {
        try {
            @SuppressWarnings("resource")
            FileWriter cetak = new FileWriter(filename);
            File outputTXT = new File(filename);
            System.out.println("Memeriksa keberadaan " + filename +"...");
            if (!outputTXT.exists()) {
                System.out.println(filename + " tidak ada! Membuat " + filename + ".");
                outputTXT.createNewFile();
                System.out.println(filename + " berhasil dibuat.");
            } else {System.out.println(filename + " ditemukan!");}
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void writeTXT(String filename, MatrixADT outputMatrix) {
        File outputTXT = new File(filename);
            try (FileWriter writer = new FileWriter(outputTXT, false)) {
                for (int i = 0; i < outputMatrix.getRows(); i++) {
                    String line = "";
                    for (int j = 0; j < outputMatrix.getCols(); j++) {
                        if (j == 0) {
                            line += Double.toString(outputMatrix.getElmt(i, j));
                        } else {
                            line += " " + Double.toString(outputMatrix.getElmt(i, j));
                        }
                    }
                    writer.write(line);
                    writer.write("\n");
                }
            System.out.println(filename + " berhasil disimpan!");
            }
        catch (IOException e) {
            System.out.println("Sebuah error terjadi ketika membuat atau menyimpan file.");
            e.printStackTrace();
        }
    }
}