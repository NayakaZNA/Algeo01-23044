# Tugas Besar 1 IF2123 - Aljabar Linear dan Geometri
Tugas besar yang diberikan sebagai salah satu komponen penilaian dari mata kuliah IF2123 Aljabar Linear dan Geometri. Tugas ini memuat implementasi penyelesaian SPL dengan eliminasi Gauss ataupun Gauss-Jordan perhitungan determinan dan invers matriks, dan metode Cramer beserta penerapannya dalam interpolasi dan regresi.

## Kelompok 1 - Raka Gnarly

| NIM | Nama           |
| :-------- | :------------------------- |
| 13523044 |  Muhammad Luqman Hakim |
| 13523094 | Zulfaqqar Nayaka Athadiansyah |
| 13523118 | Farrel Athalla Putra |

## Instalation / Getting Started
Berikut instruksi instalasi dan penggunaan program
1. Clone repository ke dalam suatu folder

```bash
  git clone https://github.com/NayakaZNA/Algeo01-23044.git
```

2. Pergi ke directory /Algeo01-23044/bin

```bash
  cd Algeo01-23044
  cd bin
```

3. Jalankan program!

```bash
  java UI.Main
```

## Features
- Menyelesaikan Sistem Persamaan Linear (SPL) dengan metode Gauss, Gauss-Jordan, Inverse (Balikan), dan Cramer.
- Menghitung determinan sebuah matriks dengan metode ekspansi kofaktor dan reduksi baris dengan OBE.
- Menghitung inverse sebuah matriks dengan metode Gauss-Jordan dan Adjoin.
- Menyelesaikan interpolasi polinom.
- Menyelesaikan interpolasi bicubic spline.
- Menyelesaikan permesalahan regresi linear berganda dan regresi kuadratik berganda.

## Deskripsi Singkat Fungsi
| File | Deskripsi |
| :-------- | :------------------------- |
| `InverseAdjoin.java` | Class ini mengimplementasikan algoritma untuk menghitung invers matriks menggunakan metode adjoin matriks. |
| `InverseGaussJ.java` | Class ini mengimplementasikan algoritma untuk menghitung invers matriks menggunakan metode eliminasi Gauss-Jordan. |
| `BicubicSplineInterpolation.java` | Class ini menerapkan metode interpolasi bicubic dengan menggunakan matriks m dan koordinat x serta y sebagai input. |
| `DeterminanMK.java` | Class ini menghitung determinan matriks persegi menggunakan teknik ekspansi kofaktor. |
| `DeterminanRB.java` | Class ini menghitung determinan matriks persegi dengan metode reduksi baris. |
| `InterpolasiPolinom.java` | Class ini mengimplementasikan algoritma interpolasi polinomial berdasarkan titik-titik yang diberikan. |
| `SPLBalikan.java` | Class ini menyelesaikan Sistem Persamaan Linear (SPL) menggunakan metode invers matriks. |
| `SPLCramer.java` | Class ini menggunakan aturan Cramer untuk menyelesaikan Sistem Persamaan Linear (SPL). |
| `SPLGauss.java` | Class ini menyelesaikan Sistem Persamaan Linear (SPL) melalui eliminasi Gauss. |
| `SPLGaussJ.java` | Class ini menyelesaikan Sistem Persamaan Linear (SPL) menggunakan metode eliminasi Gauss-Jordan. |
| `Regresi.java` | Class ini mengimplementasikan algoritma untuk melakukan regresi linear berganda dan regresi kuadratik berganda berdasarkan data yang dimasukkan pengguna. |
