package com.example.automessage;

public class SinhVien_Diem {
	private double diemLy;
	private double diemHoa;
	private double diemToan;
	private String mssv;

	public SinhVien_Diem() {
		super();
	}

	public SinhVien_Diem(String mssv, double diemLy, double diemHoa,
			double diemToan) {
		super();
		this.diemLy = diemLy;
		this.diemHoa = diemHoa;
		this.diemToan = diemToan;
		this.mssv = mssv;
	}

	public double getDiemLy() {
		return diemLy;
	}

	public void setDiemLy(double diemLy) {
		this.diemLy = diemLy;
	}

	public double getDiemHoa() {
		return diemHoa;
	}

	public void setDiemHoa(double diemHoa) {
		this.diemHoa = diemHoa;
	}

	public double getDiemToan() {
		return diemToan;
	}

	public void setDiemToan(double diemToan) {
		this.diemToan = diemToan;
	}

	public String getMssv() {
		return mssv;
	}

	public void setMssv(String mssv) {
		this.mssv = mssv;
	}

	@Override
	public String toString() {
		return "[KETQUA] Ly:" + diemLy + ", Hoa:" + diemHoa + ", Toan:"
				+ diemToan;
	}
}
