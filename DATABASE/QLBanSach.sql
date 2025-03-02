create database quanlybansach;
use quanlybansach;

------------------BÁN SÁCH----------------
------------------Sách -----------------------------------------------------------------------------------------------
Create table Sach
(
	id varchar(10) primary key,
	tenSach nvarchar(255),
	theLoai varchar(10),
	tacGia varchar(10),
	nhaXuatBan varchar(10),
	giaBan int,
	soLuong int,
	trangThai int,
	maKho varchar(10)
);
alter table Sach add hinhAnh varchar(255);
insert into Sach
	(id, tenSach, theLoai, tacGia, nhaXuatBan, soLuong, giaBan, maKho, hinhAnh, trangThai)
values
	('S001', N'Khới sự ăn chay', 'TL01', 'TG01', 'XB01', 5, 35000, 'K001', 'Am_Thuc_DucNguyen_TheGioi.webp', 1),
	('S002', N'Khám phá ẩm thực truyền thống Việt Nam', 'TL01', 'TG02', 'XB02', 10, 115000, 'K001', 'Am_Thuc_NDT_DHQGHN.webp', 1),
	('S003', N'Khóa Học Nấu Ăn Tại Gia Của Gordon Ramsay', 'TL01', 'TG03', 'XB01', 15, 200000, 'K001', 'Am_Thuc_GR_TG.webp', 1),
	('S004', N'Những Món Chay Ngon Và Dinh Dưỡng', 'TL01', 'TG04', 'XB03', 20, 20000, 'K002', 'Am_Thuc_YD_HD.webp', 1),
	('S005', N'Xì Xầm vs Hét To - Bí Kíp X Trong Quảng Cáo-PR', 'TL02', 'TG05', 'XB04', 3, 23000, 'K001', 'BanChay_RL_TH.webp', 1),
	('S006', N'Các món cháo ăn dặm', 'TL02', 'TG06', 'XB05', 6, 55000, 'K003', 'BanChay_TH_PN.webp', 1),
	('S007', N'Vì sao bạn ế', 'TL02', 'TG06', 'XB06', 7, 89000, 'K002', 'BanChay_SE_LDXH.webp', 1),
	('S008', N'Hóa học hữu cơ', 'TL04', 'TG08', 'XB02', 2, 135000, 'K002', 'THPT_NXT_QG.webp', 1),
	('S009', N'Cẩm Nang Pháp Luật Dành Cho Doanh Nghiệp', 'TL05', 'TG09', 'XB03', 26, 263000, 'K003', 'ChinhTriPhapLuat_BP_HD.webp', 1),
	('S010', N'Bộ Luật Dân Sự Năm 2015 Và Các Nghị Định Hướng Dẫn Thi Hành', 'TL05', 'TG10', 'XB07', 13, 160000, 'K004', 'ChinhTriPhapLuat_QH_ST.webp', 1),
	('S011', N'Bộ Luật Tố Tụng Hình Sự (Hiện Hành)', 'TL05', 'TG10', 'XB07', 16, 230000, 'K004', 'ChinhTriPhapLuat_QH_ST2.webp', 1),
	('S012', N'Cấu Trúc Dữ Liệu Và Thuật Toán', 'TL06', 'TG11', 'XB08', 23, 120000, 'K003', 'KhoaHocKyThuat_11.webp', 1),
	('S013', N'Toán Học Cao Cấp, Tập 1: Đại Số Và Hình Học Giải Tích (Tái bản lần thứ tư, năm 2024)', 'TL07', 'TG12', 'XB09', 22, 94000, 'K004', 'GiaoKhoa_12.jpg', 1),
	('S014', N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh (Tái Bản 2023)', 'TL08', 'TG13', 'XB10', 24, 136000, 'K004', 'VanHoc_1.webp', 1),
	('S015', N'Ngày xưa có một chuyện tình - Phiên bản điện ảnh', 'TL08', 'TG13', 'XB10', 2, 56000, 'K002', 'Vanhoc_2.webp', 1),
	('S016', N'Bộ sách Thiền sư Thích Nhất Hạnh Phần 1: Gieo Trồng Hạnh Phúc - Tìm Bình Yên Trong Gia Đình - Muốn An Được An - Tĩnh Lặng - Đạo Phật Ngày Nay - Con Đường Chuyển Hóa - Hướng Đi Của Đạo Bụt Cho Hòa Bình Và Sinh Môi - Tay Thầy Trong Tay Con - Bụt Là Hình Hài', 'TL09', 'TG14', 'XB06', 5, 700000, 'K001', 'TonGiao_14.webp', 1),
	('S017', N'Harry Potter and the ly Hallows by J. K. Rowling', 'TL10', 'TG16', 'XB11', 30, 1875000, 'K002', 'Truyen_1.webp', 1),
	('S018', N'One Piece Stampede', 'TL10', 'TG17', 'XB12', 12, 75000, 'K002', 'Truyen_2.webp', 1),
	('S019', N'Ngữ Pháp Tiếng Pháp Thực Hành Trong 80 Chủ Đề', 'TL11', NULL, 'XB13', 25, 110000, 'K001', 'NgoaiNgu_1.webp', 1),
	('S020', N'Tiếng Đức DEUTSCH', 'TL11', 'TG18', 'XB13', 9, 99000, 'K001', 'NgoaiNgu_2.webp', 1);
----------------- Viết -----------------------------------------------------------------------------------------------
Create table Viet
(
	maTG varchar(10),
	maSach varchar(10),
	primary key (maTG, maSach),
	vaitro NVARCHAR(255)
);


-- insert into Viet
-- 	(maTG, maSach, maSach, vaitro)
-- values
-- 	();

--------------- Thể loại --------------------------------------------------------------------------------------------
Create table TheLoai
(
	maLoai varchar(10) primary key,
	tenLoai nvarchar(255),
	trangThai int not null DEFAULT 1
);

insert into TheLoai
	(maLoai, tenLoai)
values
	('TL01', N'Ẩm thực'),
	('TL02', N'Bán chạy'),
	('TL03', N'Sách khác'),
	('TL04', N'Luyện thi THPTQG'),
	('TL05', N'Chính trị và Pháp luật'),
	('TL06', N'Khoa học và kỹ thuật'),
	('TL07', N'Giáo khoa - Giáo trình'),
	('TL08', N'Văn học'),
	('TL09', N'Tôn giáo - Tâm linh'),
	('TL10', N'Truyện'),
	('TL11', N'Ngoại ngữ');

---------------- Tác giả ----------------------------------------------------------------------------------------------

Create table TacGia
(
	maTG varchar(10) primary key,
	tenTG nvarchar(255),
	lienLac nvarchar(255),
	trangThai int not null
);
--drop table TacGia

insert into TacGia
	(maTG,tenTG, lienLac, trangThai)
values
	('TG01', N'Đức Nguyễn', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG02', N'Ngô Đức Thịnh', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG03', N'Gordon Ramsay', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG04', N'Yanny Đặng', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG05', N'Russell Lwson', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG06', N'BSCKII Nguyễn Thị Thu Hậu', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG07', N'Sara Eckel', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG08', N'PGS.TS. Nguyễn Xuân Trường', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG09', N'Tăng Bình - Ái Phương', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG10', N'Quốc Hội', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG11', N'PGS. TS. Hoàng Nghĩa Tý', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG12', N'GS.TS. Nguyễn Đình Trí (Chủ biên),  PGS. TS. Trần Việt Dũng, PGS. TS. Trần Xuân Hiển, PGS. TS. Nguyễn Xuân Hào', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG13', N'Nguyễn Nhật Ánh', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG14', N'Thiền sư Thích Nhất Hạnh', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG15', N'Ernest Hemingway', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG16', N'J.K. Rowling', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG17', N'Eiichiro Oda', N'60-62 Lê Lợi, Q.1, TP. HCM', 1),
	('TG18', N'Dương Đình Bá', N'60-62 Lê Lợi, Q.1, TP. HCM', 1);



------------------ Nhà xuất bản ---------------------------------------------------------------------------------------
Create table NhaXuatBan
(
	maNXB varchar(10) primary key,
	tenNXB nvarchar(255),
	diaChiNXB nvarchar(255),
	sdt varchar(11),
	trangThai int not null DEFAULT 1
);
insert into NhaXuatBan
	(maNXB, tenNXB, diaChiNXB, sdt, trangThai)
values
	('XB01', N'Thế giới', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB02', N'Đại Học Quốc Gia Hà Nội', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB03', N'Nhà Xuất Bản Hồng Đức', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB04', N'Tổng Hợp TPHCM', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB05', N'Nhà Xuất Bản Phụ Nữ', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB06', N'Nhà Xuất Bản Lao Động Xã Hội', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB07', N'Nhà Xuất Bản Chính Trị Quốc Gia Sự Thật', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB08', N'Nhà Xuất Bản Xây Dựng', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB09', N'Nhà Xuất Bản Giáo Dục Việt Nam', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB10', N'NXB Trẻ', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB11', N'Penguin', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB12', N'Nhà Xuất Bản Kim Đồng', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1),
	('XB13', N'Nhà Xuất Bản Đà Nẵng', N'275 An Dương Vương P16, Q8, TP. HCM', '0862761223', 1);

------------------ Hóa đơn --------------------------------------------------------------------------------------------

Create table HoaDon
(
	soHD varchar(10) primary key,
	ngayBan date,
	maNV varchar(10),
	trangThai int DEFAULT 1
);

insert into HoaDon
	(soHD, maNV, ngayBan, trangThai)
values
	('HD01', 'NV01', '2023-09-16', 1),
	('HD02', 'NV01', '2023-10-06', 1),
	('HD03', 'NV03', '2023-08-06', 1),
	('HD04', 'NV02', '2023-08-30', 1),
	('HD05', 'NV03', '2023-10-10', 1),
	('HD06', 'NV01', '2023-10-10', 1),
	('HD07', 'NV03', '2023-10-08', 1),
	('HD08', 'NV03', '2023-09-13', 1);
------------------ Chi tiết hóa đơn ------------------------------------------------------------------------------------

Create table ChiTietHoaDon
(
	maSach varchar(10),
	soHD varchar(10),
	soLuongBan int,
	giaBan int,
	primary key (maSach, soHD)
);

insert into ChiTietHoaDon
	(soHD,maSach, soLuongBan, giaBan)
values
	('HD01', null, 5, 300000),
	('HD01', null, 3, 285000),
	('HD01', null, 8, 1360000),
	('HD02', null, 3, 210000),
	('HD02', null, 2, 120000),
	('HD04', null, 3, 435000),
	('HD05', null, 1, 95000),
	('HD08', null, 4, 320000);

----------------- Phiếu nhập --------------------------------------------------------------------------------------------

Create table PhieuNhap
(
	soPN varchar(10) primary key,
	maNV varchar(10),
	maNXB varchar(10),
	maKho varchar(10),
	ngayNhap date,
	tongTien int,
	trangThai int
);
-- drop table PhieuNhap

insert into PhieuNhap
	(soPN, maNV, maNXB, maKho,ngayNhap, tongTien, trangThai)
values
	('PN01', 'NV02', 'XB01', 'K001', '2025-02-11', 70000, 1),
	('PN02', 'NV02', 'XB02', 'K001', '2025-02-11', 115000, 1),
	('PN04', 'NV02', 'XB01', 'K001', '2025-02-11', 600000, 1),
	('PN05', 'NV02', 'XB04', 'K002', '2025-02-11', 200000, 1);


----------------- Chi tiết phiếu nhập -----------------------------------------------------------------------------------
Create table ChiTietPhieuNhap
(
	maSach varchar(10),
	soPN varchar(10),
	soLuongNhap int,
	giaNhap int,
	trangThai int,
	primary key (maSach, soPN),
);
--drop table ChiTietPhieuNhap

insert into ChiTietPhieuNhap
	(soPN, maSach, soLuongNhap, giaNhap, trangThai)
values
	('PN01', 'S001', 2, 35000, 1),
	('PN02', 'S002', 1, 115000, 1),
	('PN04', 'S003', 3, 200000, 1),
	('PN05', 'S004', 1, 200000, 1);

----------------- Kho sách ----------------------------------------------------------------------------------------------

Create table KhoSach
(
	maKho varchar(10) primary key,
	tenKho nvarchar(255),
	diaChi nvarchar(255),
	loai nvarchar(255),
	trangThai int not null DEFAULT 1
);


insert into KhoSach
	(maKho, tenKho, diaChi, loai, trangThai)
values
	('K001', N'Khu vực A', N'99 Anh Dương Vương P16, Q8, TP. Hồ Chí Minh', N'Loại A', 1),
	('K002', N'Khu vực B', N'273 An Dương Vương P2, Q5, TP. Hồ Chí Minh', N'Loại A', 1),
	('K003', N'Khu vực C', N'105 Bà Huyện Thanh Quan, Phường 6, Quận 3, TP. Hồ Chí Minh', N'Loại AB', 1),
	('K004', N'Khu vực D', N'4 Tôn Đức Thắng, Bến Nghé, Quận 1, TP. Hồ Chí Minh', N'Loại A', 1);

-------------------- Nhân viên ------------------------------------------------------------------------------------------
Create table NhanVien
(
	maNV varchar(10) primary key,
	tenNV nvarchar(255),
	gioiTinh int,
	diaChi nvarchar(255),
	ngayVao date,
	sdt varchar(11),
	ngaySinh date,
	trangThai int not null
);

insert into NhanVien
	(maNV, tenNV, gioiTinh, diaChi, ngayVao, sdt, ngaySinh, trangThai)
values
	('NV01', N'Nguyễn Hoàng Mai Vy', 0, N'99 Anh Dương Vương P16, Q8, TP. Hồ Chí Minh', '2022-01-12', '0862498257', '2004-05-24', 1),
	('NV02', N'Lê Hoàng A', 0, N'273 An Dương Vương P2, Q5, TP. Hồ Chí Minh', '2022-01-14', '0123456789', '2004-05-24', 1),
	('NV03', N'Trần Nhật Sinh', 1, N'273 An Dương Vương P2, Q5, TP. Hồ Chí Minh', '2022-02-15', 'HD01', '2003-12-20', 1);

------------------- Khách hàng ------------------------------------------------------------------------------------------------

Create table KhachHang
(
	maKH varchar(10) primary key,
	tenKH nvarchar(255),
	sdt varchar(11),
	diaChi nvarchar(255),
	trangThai int,
	maHD varchar(10)
);

insert into KhachHang
	(maKH, tenKH, diaChi, maHD, sdt, trangThai)
values
	('C001', N'Nguyen Van A', N'322/15 An Duong Vuong, Quan 5, TP.HCM', 'HD01', '0238512391', 1),
	('C002', N'Hoang Duoc Su', N'213 Tran Binh Trong, Quan 5, TP.HCM', 'HD01', '03481234921', 1),
	('C003', N'Nguyen Thi Anh', N'45 Le Loi, Quan 1, TP.HCM', 'HD02', '03217894563', 1),
	('C004', N'Tran Van Minh', N'76 Nguyen Hue, Quan 3, TP.HCM', 'HD03', '03698521478', 1),
	('C005', N'Le Thi Mai', N'102 Nguyen Trai, Quan 10, TP.HCM', 'HD04', '03875213698', 1),
	('C006', N'Pham Van Tua', N'31 Tran Hung Dao, Quan 1, TP.HCM', 'HD05', '0352147896', 1),
	('C007', N'Nguyen Van Hieu', N'17 Hoang Sa, Quan 1, TP.HCM', 'HD06', '0398546213', 1);

--------------------- Tài khoản ----------------------------------------------------------------------------------

Create table TaiKhoan
(
	useName nvarchar(100),
	sdt varchar(11) primary key,
	matKhau varchar(50),
	maNV varchar(10),
	trangThai int
);

alter table TaiKhoan add maNhomQuyen int

insert into TaiKhoan
	(useName, sdt, matKhau, maNV, maNhomQuyen, trangThai)
values
	(N'Vy Nguyễn', '0862498257', '0123456789', 'NV01', 1, 1);

--------------------- Nhóm quyền -----------------------------------------------------------------------
CREATE TABLE nhomQuyen
(
	maNhomQuyen int IDENTITY(1,1) primary key,
	tenNhomQuyen nvarchar(50) NOT NULL,
	trangThai int NOT NULL
);

--
-- Dumping data for table `nhomquyen`
-- drop table nhomQuyen

INSERT INTO nhomQuyen
	(tenNhomQuyen, trangThai)
VALUES
	( N'Admin', 1),
	(N'Quản lý bán hàng', 1),
	(N'Quản lý kho', 1),
	(N'Khách hàng', 1);

SELECT *
FROM nhomQuyen
WHERE maNhomQuyen LIKE '%khách%' OR tenNhomQuyen LIKE '%khách%'






/*
-------------------Liên kết các bảng---------
after table Sach add constraint sach_tacgia_fk foreign key (tacGia) references TacGia(maTG),
					constraint sach_nhaxuatban_fk foreign key (nhaXuanBan) references NhaXuatBan(maNXB),
					constraint sach_khosach_fl,
					constraint sach_theloai_fk,
					constraint sach_;

after table TacGia;
after table NhaXuatBan;
after table 

-----------------------Xóa tất cả các bảng----------------------
-- Tạo các lệnh DROP TABLE cho tất cả các bảng
DECLARE @sql NVARCHAR(MAX) = N'';

SELECT @sql += 'DROP TABLE ' + QUOTENAME(TABLE_SCHEMA) + '.' + QUOTENAME(TABLE_NAME) + '; '
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE';

EXEC sp_executesql @sql;

*/