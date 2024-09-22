use QLNV

INSERT INTO [dbo].[Degrees] ([degreeId], [degreeName], [deleteStatus])
VALUES
('DE001', 'Không có', '0'),
('DE002', 'Chứng Chỉ Nghề', '0'),
('DE003', 'Cử Nhân', '0'),
('DE004', 'Kỹ Sư', '0'),
('DE005', 'Thạc Sĩ', '0'),
('DE006', 'Tiến Sĩ', '0'),
('DE007', 'Giáo Sư', '0');

INSERT INTO [dbo].[Positions] ([positionId], [positionName], [deleteStatus], [positionSalaryAllowance])
VALUES
('PO001', 'Nhân Viên', '0', 0.03),
('PO002', 'Phó Phòng', '0', 0.07),
('PO003', 'Trưởng Phòng', '0', 0.15),
('PO004', 'Phó Giám Đốc', '0', 0.25),
('PO005', 'Giám Đốc', '0', 0.4);

INSERT INTO [dbo].[Criticism] ([criticismId], [criticismName], [judgement], [deleteStatus])
VALUES
('CR001', 'Không có lỗi', 0, '0'),
('CR002', 'Đi trễ', 100000, '0'),
('CR003', 'Vắng', 300000, '0'),
('CR004', 'Trang phục thiếu nghiêm túc', 200000, '0'),
('CR005', 'Nói tục chửi thề trong giờ làm việc', 400000, '0'),
('CR006', 'Trễ deadline từ 1 đến 2 ngày', 500000, '0'),
('CR007', 'Trễ deadline từ 3 ngày trở lên', 1000000, '0');

INSERT INTO [dbo].[Rewards] ([rewardId], [rewardName], [reward], [deleteStatus])
VALUES
('RE001', 'Không có thưởng', 0, '0'),
('RE002', 'Thưởng Tết', 5000000, '0'),
('RE003', 'Thưởng dự án', 20000000, '0'),
('RE004', 'Hoàn thành deadline từ 1 đến 2 ngày', 100000, '0'),
('RE005', 'Hoàn thành deadline từ 3 ngày trở lên', 300000, '0');

INSERT INTO [dbo].[Specialties] ([specialtyId], [specialtyName], [baseSalary], [deleteStatus])
VALUES
('SP001', 'Kế Toán', 10000000, '0'),
('SP002', 'Full-stack Developer Intern', 8000000, '0'),
('SP003', 'Full-stack Developer Fresher', 10000000, '0'),
('SP004', 'Full-stack Developer Junior', 25000000, '0'),
('SP005', 'Full-stack Developer Senior', 50000000, '0'),
('SP006', 'Marketing Intern', 5000000, '0'),
('SP007', 'Marketing Fresher', 7000000, '0'),
('SP008', 'Marketing Junior', 15000000, '0'),
('SP009', 'Đầu Bếp', 12000000, '0'),
('SP010', 'Bảo Vệ', 6000000, '0'),
('SP011', 'Designer Intern', 8000000, '0'),
('SP012', 'Designer Fresher', 10000000, '0'),
('SP013', 'Designer Junior', 20000000, '0'),
('SP014', 'Data Engineer Intern', 9000000, '0'),
('SP015', 'Data Engineer Fresher', 12000000, '0'),
('SP016', 'Data Engineer Junior', 29000000, '0'),
('SP017', 'Data Engineer Senior', 60000000, '0'),
('SP018', 'Tester Fresher', 6000000, '0'),
('SP019', 'Tester Junior', 10000000, '0'),
('SP020', 'Tester Senior', 20000000, '0'),
('SP021', 'Project Manager', 40000000, '0'),
('SP022', 'Lao Công', 8000000, '0');

INSERT INTO [QLNV].[dbo].[Departments] ([departmentId], [departmentName], [departmentLeader], [deleteStatus])
VALUES 
('DP000', 'Không có phòng ban', 'ADM001', '1'),
('DP001', 'Phòng Bảo Vệ', 'EM005', '0'),
('DP002', 'Phòng bếp', 'EM006', '0'),
('DP003', 'Phòng tài chính - Marketing', 'EM007', '0'),
('DP004', 'Phòng phát triển ứng dụng 1', 'EM028', '0'),
('DP005', 'Phòng phát triển ứng dụng 2', 'EM029', '0'),
('DP006', 'Phòng lao công', 'EM021', '0'),
('DP007', 'Phòng quản lý', 'EM031', '0');

INSERT INTO [QLNV].[dbo].[Employees] (
    [id],
    [fullName],
    [birthDate],
    [phoneNumber],
    [gender],
    [ethnicGroup],
    [religion],
    [nation],
    [positionId],
    [specialtyId],
    [degreeId],
    [departmentId],
    [type],
    [employStatus], 
	[deleteStatus]
)
VALUES 
('EM001', 'Cao Thái Bảo', '2000-01-01', '0832156378', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP003', 'DE004', 'DP004', 'Chính Thức', '1', '0'),
('EM002', 'Lê Thị Minh', '1990-05-15', '0832156379', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP001', 'DE003', 'DP003', 'Chính Thức', '1', '0'),
('EM003', 'Phạm Văn Khánh', '2000-09-20', '0832156380', 'Nam', 'Kinh', 'Thiên Chúa Giáo', 'Việt Nam', 'PO001', 'SP003', 'DE004', 'DP005', 'Chính Thức', '1', '0'),
('EM004', 'Nguyễn Thị Quỳnh', '1976-11-03', '0832156381', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP022', 'DE001', 'DP006', 'Chính Thức', '1', '0'),
('EM005', 'Trần Văn Toàn', '1980-12-12', '0832156382', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO003', 'SP010', 'DE001', 'DP001', 'Chính Thức', '1', '0'),
('EM006', 'Lê Thị Hằng', '1980-08-09', '0832156383', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO003', 'SP009', 'DE002', 'DP002', 'Chính Thức', '1', '0'),
('EM007', 'Đỗ Thị Hương', '1985-02-28', '0832156384', 'Nữ', 'Kinh', 'Thiên Chúa Giáo', 'Việt Nam', 'PO003', 'SP001', 'DE003', 'DP003', 'Chính Thức', '1', '0'),
('EM008', 'Nguyễn Văn Hiếu', '1970-06-14', '0832156385', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP010', 'DE001', 'DP001', 'Chính Thức', '1', '0'),
('EM009', 'Trần Thị Lan', '1980-03-19', '0832156386', 'Nữ', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP009', 'DE002', 'DP002', 'Chính Thức', '1', '0'),
('EM010', 'Hoàng Văn Hùng', '1970-10-10', '0832156387', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP010', 'DE001', 'DP001', 'Chính Thức', '1', '0'),
('EM011', 'Lê Thị Thảo', '1990-07-04', '0832156388', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP001', 'DE003', 'DP003', 'Chính Thức', '1', '0'),
('EM012', 'Phạm Văn Tuấn', '2000-11-27', '0832156389', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP016', 'DE005', 'DP004', 'Chính Thức', '1', '0'),
('EM013', 'Nguyễn Thị Tuyết', '2000-09-08', '0832156390', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO004', 'SP012', 'DE006', 'DP007', 'Chính Thức', '1', '0'),
('EM014', 'Trần Văn Tuân', '1990-01-21', '0832156391', 'Nam', 'Kinh', 'Thiên Chúa Giáo', 'Việt Nam', 'PO004', 'SP016', 'DE005', 'DP007', 'Chính Thức', '1', '0'),
('EM015', 'Đặng Thị Mai', '1980-04-30', '0832156392', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP022', 'DE001', 'DP006', 'Chính Thức', '1', '0'),
('EM016', 'Nguyễn Văn An', '1990-03-05', '0832156393', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP013', 'DE005', 'DP004', 'Chính Thức', '1', '0'),
('EM017', 'Lê Thị Thu', '1985-12-18', '0832156394', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP013', 'DE005', 'DP005', 'Chính Thức', '1', '0'),
('EM018', 'Hoàng Thị Hạnh', '1980-08-23', '0832156395', 'Nữ', 'Kinh', 'Thiên Chúa Giáo', 'Việt Nam', 'PO001', 'SP020', 'DE006', 'DP005', 'Chính Thức', '1', '0'),
('EM019', 'Trịnh Văn Hòa', '1985-10-02', '0832156396', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP004', 'DE004', 'DP005', 'Chính Thức', '1', '0'),
('EM020', 'Nguyễn Thị Thu Hà', '1985-06-16', '0832156397', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP020', 'DE006', 'DP004', 'Chính Thức', '1', '0'),
('EM021', 'Võ Thị Hoàng', '1976-05-14', '0832156398', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP022', 'DE001', 'DP006', 'Chính Thức', '1', '0'),
('EM022', 'Lâm Văn Hưng', '1985-01-09', '0832156399', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP004', 'DE004', 'DP004', 'Chính Thức', '1', '0'),
('EM023', 'Huỳnh Thị Thanh', '2000-03-20', '0832156400', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP019', 'DE003', 'DP004', 'Chính Thức', '1', '0'),
('EM024', 'Nguyễn Thị Thùy', '2000-07-07', '0832156401', 'Nữ', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP007', 'DE003', 'DP003', 'Chính Thức', '1', '0'),
('EM025', 'Trần Văn Hoàng', '1990-12-12', '0832156402', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP008', 'DE005', 'DP003', 'Chính Thức', '1', '0'),
('EM026', 'Phạm Thị Ngọc', '2000-09-25', '0832156403', 'Nữ', 'Kinh', 'Thiên Chúa Giáo', 'Việt Nam', 'PO001', 'SP008', 'DE005', 'DP003', 'Chính Thức', '1', '0'),
('EM027', 'Nguyễn Thị Hà', '1970-10-19', '0832156404', 'Nữ', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP021', 'DE006', 'DP005', 'Chính Thức', '1', '0'),
('EM028', 'Lê Văn Đức', '1976-08-11', '0832156405', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO003', 'SP005', 'DE005', 'DP004', 'Chính Thức', '1', '0'),
('EM029', 'Hoàng Thị Hằng', '1976-04-04', '0832156406', 'Nữ', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO003', 'SP017', 'DE006', 'DP005', 'Chính Thức', '1', '0'),
('EM030', 'Trịnh Văn Đông', '1970-06-08', '0832156407', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO001', 'SP021', 'DE006', 'DP005', 'Chính Thức', '1', '0'),
('EM031', 'Võ Văn Hiếu', '1976-05-14', '0832156408', 'Nam', 'Kinh', 'Không', 'Việt Nam', 'PO005', 'SP021', 'DE007', 'DP007', 'Chính Thức', '1', '0'),
('ADM001', 'Trần Đức Hiển', '2004-09-23', '0123456789', 'Nam', 'Kinh', 'Phật Giáo', 'Việt Nam', 'PO001', 'SP005', 'DE004', 'DP007', 'Chính Thức', '1', '0');

INSERT INTO [QLNV].[dbo].[Projects] ([projectId], [projectName], [departmentId], [beginAt], [completedAt], [place], [deleteStatus])
VALUES 
('PJ001', 'Dự án phát triển web bán hàng Shopee', 'DP004', GETDATE(), DATEADD(MONTH, 6, GETDATE()), 'TPHCM', '0'),
('PJ002', 'Dự án phát triển ứng dụng quản lý bệnh viện', 'DP005', GETDATE(), DATEADD(MONTH, 6, GETDATE()), 'Hà Nội', '0');


INSERT INTO [QLNV].[dbo].[Account] ([userId], [username], [password], [email], [avatar], [authorization], [accountStatus], [deleteStatus])
VALUES 
('EM001', 'caothaibao', '123456', 'caothaibao@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM002', 'lethiminh', '123456', 'lethiminh@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM003', 'phamvankhanh', '123456', 'phamvankhanh@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM004', 'nguyenthiquynh', '123456', 'nguyenthiquynh@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM005', 'tranvantoan', '123456', 'tranvantoan@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM006', 'lethihang', '123456', 'lethihang@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM007', 'dothihuong', '123456', 'dothihuong@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM008', 'nguyenvanhieu', '123456', 'nguyenvanhieu@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM009', 'tranthilan', '123456', 'tranthilan@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM010', 'hoangvanhung', '123456', 'hoangvanhung@example.com', 'test-avatar.png', 'employee', '1', '0'),
('EM011', 'lethithao', '123456', 'lethithao@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM012', 'phamvantuan', '123456', 'phamvantuan@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM013', 'nguyenthituyet', '123456', 'nguyenthituyet@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM014', 'tranvantuan', '123456', 'tranvantuan@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM015', 'dangthimai', '123456', 'dangthimai@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM016', 'nguyenvanan', '123456', 'nguyenvanan@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM017', 'lethithu', '123456', 'lethithu@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM018', 'hoangvanhanh', '123456', 'hoangvanhanh@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM019', 'trinhvanhoa', '123456', 'trinhvanhoa@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM020', 'nguyenthithuha', '123456', 'nguyenthithuha@example.com', 'test-avatar2.png', 'employee', '1', '0'),
('EM021', 'vothihoang', '123456', 'vothihoang@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM022', 'lamvanhung', '123456', 'lamvanhung@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM023', 'huynhthithanh', '123456', 'huynhthithanh@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM024', 'nguyenthithuy', '123456', 'nguyenthithuy@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM025', 'tranvanhoang', '123456', 'tranvanhoang@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM026', 'phamthingoc', '123456', 'phamthingoc@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM027', 'nguyenthiha', '123456', 'nguyenthiha@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM028', 'levanduc', '123456', 'levanduc@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM029', 'hoangthihang', '123456', 'hoangthihang@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM030', 'trinhvandong', '123456', 'trinhvandong@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('EM031', 'vovanhieu', '123456', 'vovanhieu@example.com', 'test-avatar3.png', 'employee', '1', '0'),
('ADM001', 'admin', 'admin', 'admin@example.com', 'test-avatar.png', 'admin', '1', '0');

INSERT INTO [QLNV].[dbo].[Assignments] ([employeeId], [projectId], [deleteStatus])
VALUES 
('EM001', 'PJ001', '0'),
('EM003', 'PJ002', '0'),
('EM012', 'PJ001', '0'),
('EM016', 'PJ001', '0'),
('EM017', 'PJ002', '0'),
('EM018', 'PJ002', '0'),
('EM019', 'PJ002', '0'),
('EM020', 'PJ001', '0'),
('EM022', 'PJ001', '0'),
('EM023', 'PJ001', '0'),
('EM027', 'PJ002', '0'),
('EM028', 'PJ001', '0'),
('EM029', 'PJ002', '0'),
('EM030', 'PJ002', '0');

INSERT INTO [QLNV].[dbo].[EmployeesRewardsCriticism] ([employeeId], [rewardId], [criticismId], [faultCount], [rewardCount], [createdAt])
VALUES 
('EM001', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM002', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM003', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM004', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM005', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM006', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM007', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM008', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM009', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM010', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM011', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM012', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM013', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM014', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM015', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM016', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM017', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM018', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM019', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM020', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM021', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM022', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM023', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM024', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM025', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM026', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM027', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM028', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM029', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM030', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM031', 'RE002', 'CR001', 0, 1, GETDATE()),
('EM001', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM003', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM012', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM016', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM017', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM018', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM019', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM020', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM022', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM023', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM027', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM028', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM029', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM030', 'RE003', 'CR001', 0, 1, GETDATE()),
('EM001', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM002', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM003', 'RE001', 'CR002', 2, 0, GETDATE()),
('EM004', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM005', 'RE001', 'CR002', 2, 0, GETDATE()),
('EM006', 'RE001', 'CR002', 3, 0, GETDATE()),
('EM007', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM008', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM009', 'RE001', 'CR002', 1, 0, GETDATE()),
('EM010', 'RE001', 'CR002', 1, 0, GETDATE());

INSERT INTO EmployeeSalaries VALUES 
('EM001', 10.5, 32172565, GETDATE(), 0),
('EM002', 10.5, 13735565, GETDATE(), 0),
('EM003', 10.5, 32080380, GETDATE(), 0),
('EM004', 10.5, 11891865, GETDATE(), 0),
('EM005', 10.5, 11115900, GETDATE(), 0);