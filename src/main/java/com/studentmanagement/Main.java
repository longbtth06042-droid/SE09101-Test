package com.studentmanagement;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        manager.seedData();
        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Hiển thị tất cả sinh viên");
            System.out.println("3. Tìm kiếm sinh viên");
            System.out.println("4. Cập nhật sinh viên");
            System.out.println("5. Xóa sinh viên");
            System.out.println("6. Sắp xếp sinh viên");
            System.out.println("7. Thống kê và học bổng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> displayStudents(manager.getAllStudents());
                case "3" -> searchMenu();
                case "4" -> updateStudent();
                case "5" -> removeStudent();
                case "6" -> sortMenu();
                case "7" -> statisticsMenu();
                case "0" -> {
                    System.out.println("Kết thúc chương trình.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n--- Thêm sinh viên ---");
        System.out.print("Mã sinh viên: ");
        String id = scanner.nextLine().trim();
        if (manager.findById(id).isPresent()) {
            System.out.println("Mã sinh viên đã tồn tại.");
            return;
        }
        System.out.print("Họ và tên: ");
        String fullName = scanner.nextLine().trim();
        LocalDate dob = readDate("Ngày sinh (dd/MM/yyyy): ");
        System.out.print("Giới tính: ");
        String gender = scanner.nextLine().trim();
        System.out.print("Ngành: ");
        String major = scanner.nextLine().trim();
        double gpa = readDouble("Điểm trung bình: ");
        System.out.print("Trạng thái: ");
        String status = scanner.nextLine().trim();

        Student student = new Student(id, fullName, dob, gender, major, gpa, status);
        manager.addStudent(student);
        System.out.println("Thêm sinh viên thành công.");
    }

    private static void displayStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Không có sinh viên nào.");
            return;
        }
        System.out.println("\nMã | Họ tên | Ngày sinh | Giới tính | Ngành | GPA | Trạng thái | Học lực");
        System.out.println("-------------------------------------------------------------------------------");
        students.forEach(System.out::println);
    }

    private static void searchMenu() {
        System.out.println("\n--- Tìm kiếm ---");
        System.out.println("1. Theo mã sinh viên");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Theo ngành");
        System.out.print("Chọn: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Nhập mã: ");
                String id = scanner.nextLine().trim();
                Optional<Student> student = manager.findById(id);
                student.ifPresentOrElse(
                        s -> displayStudents(List.of(s)),
                        () -> System.out.println("Không tìm thấy sinh viên."));
            }
            case "2" -> {
                System.out.print("Nhập họ tên: ");
                String name = scanner.nextLine().trim();
                displayStudents(manager.findByName(name));
            }
            case "3" -> {
                System.out.print("Nhập ngành: ");
                String major = scanner.nextLine().trim();
                displayStudents(manager.findByMajor(major));
            }
            default -> System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Cập nhật sinh viên ---");
        System.out.print("Nhập mã sinh viên cần sửa: ");
        String id = scanner.nextLine().trim();
        Optional<Student> optional = manager.findById(id);
        if (optional.isEmpty()) {
            System.out.println("Không tìm thấy sinh viên.");
            return;
        }
        Student existing = optional.get();
        System.out.print("Họ và tên (" + existing.getFullName() + "): ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) {
            fullName = existing.getFullName();
        }
        LocalDate dob = readDateWithDefault("Ngày sinh (dd/MM/yyyy) (" + existing.getDateOfBirth().format(dateFormatter) + "): ", existing.getDateOfBirth());
        System.out.print("Giới tính (" + existing.getGender() + "): ");
        String gender = scanner.nextLine().trim();
        if (gender.isEmpty()) {
            gender = existing.getGender();
        }
        System.out.print("Ngành (" + existing.getMajor() + "): ");
        String major = scanner.nextLine().trim();
        if (major.isEmpty()) {
            major = existing.getMajor();
        }
        double gpa = readDoubleWithDefault("Điểm trung bình (" + existing.getGpa() + "): ", existing.getGpa());
        System.out.print("Trạng thái (" + existing.getStatus() + "): ");
        String status = scanner.nextLine().trim();
        if (status.isEmpty()) {
            status = existing.getStatus();
        }

        Student updatedStudent = new Student(id, fullName, dob, gender, major, gpa, status);
        manager.updateStudent(id, updatedStudent);
        System.out.println("Cập nhật thành công.");
    }

    private static void removeStudent() {
        System.out.println("\n--- Xóa sinh viên ---");
        System.out.print("Nhập mã sinh viên cần xóa: ");
        String id = scanner.nextLine().trim();
        if (manager.removeStudent(id)) {
            System.out.println("Xóa thành công.");
        } else {
            System.out.println("Không tìm thấy sinh viên.");
        }
    }

    private static void sortMenu() {
        System.out.println("\n--- Sắp xếp ---");
        System.out.println("1. Theo mã");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Theo GPA giảm dần");
        System.out.print("Chọn: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> displayStudents(manager.sortById());
            case "2" -> displayStudents(manager.sortByName());
            case "3" -> displayStudents(manager.sortByGpa());
            default -> System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private static void statisticsMenu() {
        System.out.println("\n--- Thống kê và học bổng ---");
        System.out.println("1. Đếm theo ngành");
        System.out.println("2. Danh sách học bổng (GPA >= 8.0)");
        System.out.print("Chọn: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.print("Nhập ngành: ");
                String major = scanner.nextLine().trim();
                System.out.println("Số lượng sinh viên ngành " + major + ": " + manager.countByMajor(major));
            }
            case "2" -> displayStudents(manager.getHonorStudents(8.0));
            default -> System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

    private static LocalDate readDateWithDefault(String prompt, LocalDate defaultDate) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultDate;
            }
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Giá trị số không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }

    private static double readDoubleWithDefault(String prompt, double defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Giá trị số không hợp lệ. Vui lòng nhập lại.");
            }
        }
    }
}
