# SE09101-Test
Dự án Java quản lý sinh viên.

## Chạy không dùng Maven
1. Mở PowerShell hoặc Command Prompt.
2. Chuyển đến thư mục dự án:
   ```powershell
   cd "d:\SU-2026\Professional Practice\SE09101-Test"
   ```
3. Chạy file `run.bat`:
   ```powershell
   .\run.bat
   ```

## Nếu muốn chạy thủ công
1. Biên dịch:
   ```powershell
   mkdir out
   javac -d out src\main\java\com\studentmanagement\model\Student.java src\main\java\com\studentmanagement\service\StudentManager.java src\main\java\com\studentmanagement\Main.java
   ```
2. Chạy:
   ```powershell
   java -cp out com.studentmanagement.Main
   ```
