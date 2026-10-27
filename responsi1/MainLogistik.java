
package responsi1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class MainLogistik {
 
    // Modul 4
    static ArrayList<BarangBantuan> daftarBarang = new ArrayList<>();
 
    // Modul 3
    static BarangBantuan buatBarang(String kode, String nama, String kategori, double berat)
            throws BeratInvalidException {
 
        if (!kode.startsWith("EDX-")) {
            throw new IllegalArgumentException("Kode barang harus diawali EDX-");
        }
        if (berat <= 0) {
            throw new BeratInvalidException("Berat barang harus lebih dari 0 kg (input: " + berat + ")");
        }
        return new BarangBantuan(kode, nama, kategori, berat);
    }
 
    // Modul 2
    static String tentukanArmada(double totalBerat) {
        if (totalBerat < 50) {
            return "Mobil Pickup";
        } else {
            return "Truk Ekspedisi";
        }
    }
 
    // Modul 5
    static void cetakManifes(String namaFile) {
        double totalBerat = 0;
        for (BarangBantuan b : daftarBarang) {
            totalBerat += b.beratKg;
        }
        String armada = tentukanArmada(totalBerat);
 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(namaFile))) {
            bw.write("=== MANIFES PENGIRIMAN LOGISTIK PENDIDIKAN DAERAH 3T ===");
            bw.newLine();
            bw.write("----------------------------------------------------------");
            bw.newLine();
            bw.write(String.format("%-4s %-10s %-22s %-12s %10s", "No", "Kode", "Nama Barang", "Kategori", 
                    "Berat (kg)"));
            bw.newLine();
            bw.write("----------------------------------------------------------");
            bw.newLine();
 
            int no = 1;
            for (BarangBantuan b : daftarBarang) {
                bw.write(String.format("%-4d %-10s %-22s %-12s %10.2f",
                        no++, b.kodeBarang, b.namaBarang, b.kategori, b.beratKg));
                bw.newLine();
            }
 
            bw.write("----------------------------------------------------------");
            bw.newLine();
            bw.write("Total Barang : " + daftarBarang.size() + " item");
            bw.newLine();
            bw.write(String.format("Total Berat  : %.2f kg", totalBerat));
            bw.newLine();
            bw.write("Armada       : " + armada);
            bw.newLine();
 
            System.out.println("Data manifes berhasil diekspor ke " + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
        }
    }
 
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan = 0;
 
        // Modul 2
        do {
            System.out.println("\n=== SISTEM LOGISTIK BANTUAN 3T ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Daftar Barang");
            System.out.println("3. Selesai dan Cetak Manifes");
            System.out.print("Pilih menu: ");
 
            try {
                pilihan = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input menu harus berupa angka!");
                pilihan = 0;
                continue;
            }
 
            switch (pilihan) {
                case 1:
                    tambahBarang(input);
                    break;
                case 2:
                    lihatDaftar();
                    break;
                case 3:
                    if (daftarBarang.isEmpty()) {
                        System.out.println("Belum ada barang yang didata. Tambahkan barang terlebih dahulu.");
                        pilihan = 0; // kembali ke menu
                    } else {
                        cetakManifes("manifest_pengiriman.txt");
                    }
                    break;
                default:
                    System.out.println("Menu tidak tersedia!");
            }
        } while (pilihan != 3);
 
        input.close();
    }
 
    // Input barang, diulang sampai valid (try-catch-finally)
    static void tambahBarang(Scanner input) {
        boolean valid = false;
 
        while (!valid) {
            try {
                System.out.print("Kode barang (awali EDX-) : ");
                String kode = input.nextLine().trim();
                System.out.print("Nama barang              : ");
                String nama = input.nextLine().trim();
                System.out.print("Kategori                 : ");
                String kategori = input.nextLine().trim();
                System.out.print("Berat (kg)               : ");
                double berat = Double.parseDouble(input.nextLine().trim().replace(',', '.'));
 
                BarangBantuan barang = buatBarang(kode, nama, kategori, berat);
                daftarBarang.add(barang);
                System.out.println("Barang berhasil ditambahkan.");
                valid = true;
 
            } catch (BeratInvalidException e) {
                System.out.println("Galat Berat: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                // NumberFormatException adalah turunan IllegalArgumentException
                if (e instanceof NumberFormatException) {
                    System.out.println("Galat Format: berat harus berupa angka!");
                } else {
                    System.out.println("Galat Kode: " + e.getMessage());
                }
            } finally {
                if (!valid) {
                    System.out.println("Silakan ulangi input barang.\n");
                }
            }
        }
    }
 
    static void lihatDaftar() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Belum ada barang yang didata.");
            return;
        }
        System.out.println("\n--- Daftar Muatan ---");
        for (BarangBantuan b : daftarBarang) {
            b.tampilkan();
        }
    }
}
 


