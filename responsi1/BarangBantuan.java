
package responsi1;

public class BarangBantuan {
    // Atribut (Modul 1) 
    String kodeBarang; 
    String namaBarang; 
    String kategori; 
    double beratKg;
    
    // Constructor (Modul 1) 
    public BarangBantuan(String kodeBarang, String namaBarang, String kategori, double beratKg) { 
        this.kodeBarang = kodeBarang; 
        this.namaBarang = namaBarang; 
        this.kategori = kategori; 
        this.beratKg = beratKg; 
    }

    public void tampilkan(){
        System.out.printf("%-10s | %-22s | %-12s | %8.2f kg%n", kodeBarang, namaBarang, kategori, beratKg);
    }
}
