import java.util.*;

public class PatikaStore {

    private static class Marka {
        int id;
        String ad;

        public Marka(int id, String ad) {
            this.id = id;
            this.ad = ad;
        }
    }

    private static class Urun {
        int urunNo;
        String ad;
        double birimFiyat;
        double indirimOrani;
        int stokMiktari;
        Marka marka;

        public Urun(int urunNo, String ad, double birimFiyat, double indirimOrani, int stokMiktari, Marka marka) {
            this.urunNo = urunNo;
            this.ad = ad;
            this.birimFiyat = birimFiyat;
            this.indirimOrani = indirimOrani;
            this.stokMiktari = stokMiktari;
            this.marka = marka;
        }

        @Override
        public String toString() {
            return "Urun No: " + urunNo + ", Ad: " + ad + ", Birim Fiyat: " + birimFiyat +
                    ", İndirim Oranı: " + indirimOrani + ", Stok Miktarı: " + stokMiktari +
                    ", Marka: " + marka.ad;
        }
    }

    private static List<Marka> markalar = new ArrayList<>();
    private static List<Urun> cepTelefonlari = new ArrayList<>();
    private static List<Urun> notebooklar = new ArrayList<>();

    public static void main(String[] args) {
        markaEkle(new Marka(1, "Samsung"));
        markaEkle(new Marka(2, "Lenovo"));
        markaEkle(new Marka(3, "Apple"));
        markaEkle(new Marka(4, "Huawei"));
        markaEkle(new Marka(5, "Casper"));
        markaEkle(new Marka(6, "Asus"));
        markaEkle(new Marka(7, "HP"));
        markaEkle(new Marka(8, "Xiaomi"));
        markaEkle(new Marka(9, "Monster"));

        Scanner scanner = new Scanner(System.in);

        boolean devam = true;
        while (devam) {
            System.out.println("\nPatikaStore Ürün Yönetim Sistemi");
            System.out.println("1. Cep Telefonları Listesi");
            System.out.println("2. Notebook Listesi");
            System.out.println("3. Ürün Ekle");
            System.out.println("4. Ürün Sil");
            System.out.println("5. Markaya Göre Filtrele");
            System.out.println("6. Çıkış");
            System.out.print("Seçiminizi yapın: ");
            int secim = scanner.nextInt();

            switch (secim) {
                case 1:
                    urunleriListele(cepTelefonlari);
                    break;
                case 2:
                    urunleriListele(notebooklar);
                    break;
                case 3:
                    urunEkle(scanner);
                    break;
                case 4:
                    urunSil(scanner);
                    break;
                case 5:
                    markayaGoreFiltrele(scanner);
                    break;
                case 6:
                    devam = false;
                    System.out.println("Çıkış yapıldı. İyi günler!");
                    break;
                default:
                    System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }

        scanner.close();
    }

    private static void markaEkle(Marka marka) {
        markalar.add(marka);
        Collections.sort(markalar, Comparator.comparing(m -> m.ad.toLowerCase()));
    }

    private static void urunleriListele(List<Urun> urunler) {
        if (urunler.isEmpty()) {
            System.out.println("Ürün bulunamadı.");
        } else {
            for (Urun urun : urunler) {
                System.out.println(urun);
            }
        }
    }

    private static void urunEkle(Scanner scanner) {
        System.out.print("Ürün Adı: ");
        String ad = scanner.next();

        System.out.print("Birim Fiyat: ");
        double birimFiyat = scanner.nextDouble();

        System.out.print("İndirim Oranı: ");
        double indirimOrani = scanner.nextDouble();

        System.out.print("Stok Miktarı: ");
        int stokMiktari = scanner.nextInt();

        System.out.println("Mevcut Markalar:");
        markalariListele();
        System.out.print("Marka ID'sini girin: ");
        int markaId = scanner.nextInt();

        Marka marka = markaBul(markaId);
        if (marka == null) {
            System.out.println("Geçersiz marka ID'si. Ürün eklenemedi.");
            return;
        }

        System.out.print("Ürün No: ");
        int urunNo = scanner.nextInt();

        System.out.println("Ürün Grubunu Seçin:");
        System.out.println("1. Cep Telefonu");
        System.out.println("2. Notebook");
        System.out.print("Seçiminizi yapın: ");
        int secim = scanner.nextInt();

        if (secim == 1) {
            cepTelefonlari.add(new Urun(urunNo, ad, birimFiyat, indirimOrani, stokMiktari, marka));
            System.out.println("Cep telefonu eklendi.");
        } else if (secim == 2) {
            notebooklar.add(new Urun(urunNo, ad, birimFiyat, indirimOrani, stokMiktari, marka));
            System.out.println("Notebook eklendi.");
        } else {
            System.out.println("Geçersiz seçim. Ürün eklenemedi.");
        }
    }

    private static void markalariListele() {
        for (Marka marka : markalar) {
            System.out.println(marka.id + ". " + marka.ad);
        }
    }

    private static Marka markaBul(int markaId) {
        for (Marka marka : markalar) {
            if (marka.id == markaId) {
                return marka;
            }
        }
        return null;
    }

    private static void urunSil(Scanner scanner) {
        System.out.print("Silinecek ürünün No'sunu girin: ");
        int urunNo = scanner.nextInt();

        boolean silindi = false;
        silindi = urunSil(cepTelefonlari, urunNo);
        if (!silindi) {
            silindi = urunSil(notebooklar, urunNo);
        }

        if (silindi) {
            System.out.println("Ürün başarıyla silindi.");
        } else {
            System.out.println("Böyle bir ürün bulunamadı.");
        }
    }

    private static boolean urunSil(List<Urun> urunler, int urunNo) {
        for (Urun urun : urunler) {
            if (urun.urunNo == urunNo) {
                urunler.remove(urun);
                return true;
            }
        }
        return false;
    }

    private static void markayaGoreFiltrele(Scanner scanner) {
        System.out.println("Filtrelemek istediğiniz marka adını girin: ");
        String markaAdi = scanner.next();

        List<Urun> filtrelenmisUrunler = new ArrayList<>();
        for (Urun urun : cepTelefonlari) {
            if (urun.marka.ad.equalsIgnoreCase(markaAdi)) {
                filtrelenmisUrunler.add(urun);
            }
        }

        for (Urun urun : notebooklar) {
            if (urun.marka.ad.equalsIgnoreCase(markaAdi)) {
                filtrelenmisUrunler.add(urun);
            }
        }

        urunleriListele(filtrelenmisUrunler);
    }
}
