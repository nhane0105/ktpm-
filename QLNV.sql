use QLNV

CREATE TABLE [Employees] (
  [id] nvarchar(255) PRIMARY KEY,
  [fullName] nvarchar(255) not null,
  [gender] nvarchar(255) not null,
  [birthDate] DATE not null,
  [phoneNumber] nvarchar(255) not null,
  [ethnicGroup] nvarchar(255) not null,
  [type] nvarchar(255) not null,
  [religion] nvarchar(255) not null,
  [degreeId] nvarchar(255) not null,
  [nation] nvarchar(255) not null,
  [positionId] nvarchar(255) not null,
  [departmentId] nvarchar(255) not null,
  [specialtyId] nvarchar(255) not null,
  [employStatus] bit not null,
  [deleteStatus] bit not null
)
GO


CREATE TABLE [Account] (
  [userId] nvarchar(255) PRIMARY KEY,
  [username] nvarchar(255) not null,
  [password] nvarchar(255) not null,
  [email] nvarchar(255) not null,
  [avatar] nvarchar(255),
  [authorization] nvarchar(255) not null,
  [accountStatus] bit not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Degrees] (
  [degreeId] nvarchar(255) PRIMARY KEY,
  [degreeName] nvarchar(255) not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Positions] (
  [positionId] nvarchar(255) PRIMARY KEY,
  [positionName] nvarchar(255) not null,
  [positionSalaryAllowance] numeric(5,3) not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Rewards] (
  [rewardId] nvarchar(255) PRIMARY KEY,
  [rewardName] nvarchar(255) not null,
  [reward] int not null, 
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Criticism] (
  [criticismId] nvarchar(255) PRIMARY KEY,
  [criticismName] nvarchar(255) not null,
  [judgement] int not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [EmployeesRewardsCriticism] (
  [employeeId] nvarchar(255),
  [rewardId] nvarchar(255),
  [criticismId] nvarchar(255),
  [faultCount] integer not null,
  [rewardCount] integer not null,
  [createdAt] DATE,
  PRIMARY KEY ([employeeId], [rewardId], [criticismId], [createdAt])
)
GO

CREATE TABLE [Specialties] (
  [specialtyId] nvarchar(255) PRIMARY KEY,
  [specialtyName] nvarchar(255) not null,
  [baseSalary] int not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [EmployeeSalaries] (
  [employeeId] nvarchar(255),
  [insurance] decimal(18,1) not null,
  [netSalary] decimal not null,
  [createdAt] DATE not null,
  [deleteStatus] bit not null
  PRIMARY KEY ([employeeId], [createdAt])
)
GO

CREATE TABLE [Departments] (
  [departmentId] nvarchar(255) PRIMARY KEY,
  [departmentName] nvarchar(255) not null,
  [departmentLeader] nvarchar(255) not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Projects] (
  [projectId] nvarchar(255) PRIMARY KEY,
  [projectName] nvarchar(255) not null,
  [departmentId] nvarchar(255) not null,
  [beginAt] DATE not null,
  [completedAt] DATE not null,
  [place] nvarchar(255) not null,
  [deleteStatus] bit not null
)
GO

CREATE TABLE [Assignments] (
  [employeeId] nvarchar(255),
  [projectId] nvarchar(255),
  [deleteStatus] bit not null
  PRIMARY KEY ([employeeId], [projectId])
)
GO

ALTER TABLE [Account] ADD FOREIGN KEY ([userId]) REFERENCES [Employees] ([id])
GO

ALTER TABLE [Employees] ADD FOREIGN KEY ([degreeId]) REFERENCES [Degrees] ([degreeId])
GO

ALTER TABLE [Employees] ADD FOREIGN KEY ([positionId]) REFERENCES [Positions] ([positionId])
GO

ALTER TABLE [EmployeesRewardsCriticism] ADD FOREIGN KEY ([rewardId]) REFERENCES [Rewards] ([rewardId]);
GO

ALTER TABLE [EmployeesRewardsCriticism] ADD FOREIGN KEY ([criticismId]) REFERENCES [Criticism] ([criticismId]);
GO


ALTER TABLE [EmployeesRewardsCriticism] ADD FOREIGN KEY ([employeeId]) REFERENCES [Employees] ([id])
GO

ALTER TABLE [Employees] ADD FOREIGN KEY ([specialtyId]) REFERENCES [Specialties] ([specialtyId])
GO

ALTER TABLE [EmployeeSalaries] ADD FOREIGN KEY ([employeeId]) REFERENCES [Employees] ([id])
GO

ALTER TABLE [Employees] ADD FOREIGN KEY ([departmentId]) REFERENCES [Departments] ([departmentId])
GO

ALTER TABLE [Departments] ADD FOREIGN KEY ([departmentLeader]) REFERENCES [Employees] ([id])
GO

ALTER TABLE [Projects] ADD FOREIGN KEY ([departmentId]) REFERENCES [Departments] ([departmentId])
GO

ALTER TABLE [Assignments] ADD FOREIGN KEY ([employeeId]) REFERENCES [Employees] ([id])
GO

ALTER TABLE [Assignments] ADD FOREIGN KEY ([projectId]) REFERENCES [Projects] ([projectId])
GO
