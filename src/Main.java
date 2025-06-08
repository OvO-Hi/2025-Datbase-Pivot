import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    // JDBC 연결
    private static final String DB_URL = "jdbc:mysql://www.i-kina.co.kr:23406/ewha";
    private static final String USER = "ewha";
    private static final String PASSWORD = "ewhaPass2025##";

    public static void main(String[] args) {
        // DB 연결 및 콘솔 메뉴 시작
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n┌──────────────────────────────────────────────────┐");
                System.out.println("    🍓🎂        피벗들의 케이크 구움터        🍰🍒   ");
                System.out.println("          맛있는 케이크와 함께하는 달콤한 하루!              ");
                System.out.println("└──────────────────────────────────────────────────┘");
                // 메인 메뉴 출력
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("0. 종료");
                System.out.print("번호를 입력해주세요 >> ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    // 로그인 처리
                    System.out.print("아이디를 입력하세요: ");
                    String loginId = sc.nextLine();
                    System.out.print("비밀번호를 입력하세요: ");
                    String pw = sc.nextLine();

                    // 로그인 SQL: 사용자 인증
                    String loginSQL = "SELECT user_id, user_name, is_admin FROM `user` WHERE login_id = ? AND pw = ?";
                    // PreparedStatement → 로그인 정보 확인 후 로그인 성공 여부 판단
                    // 로그인 성공 시 일반 사용자 or 관리자 메뉴 분기
                    PreparedStatement psLogin = conn.prepareStatement(loginSQL);
                    psLogin.setString(1, loginId);
                    psLogin.setString(2, pw);
                    ResultSet rsLogin = psLogin.executeQuery();

                    if (rsLogin.next()) {
                        int userId = rsLogin.getInt("user_id");
                        String name = rsLogin.getString("user_name");
                        int isAdmin = rsLogin.getInt("is_admin");

                        System.out.println("✅ 로그인 성공! " + name + "님 환영합니다!");

                        if (isAdmin == 1) {
                            while (true) {
                                System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                System.out.println("👑 [관리자 메뉴]");
                                System.out.println("1. 케이크 관리");
                                System.out.println("2. 주문 관리");
                                System.out.println("3. 가게 관리");
                                System.out.println("0. 로그아웃");
                                System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                System.out.print("번호를 입력해주세요 >> ");
                                int adminMenu = sc.nextInt();
                                sc.nextLine();

                                if (adminMenu == 1) {
                                    while(true){
                                        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.println("🎂 [관리자 메뉴] - [케이크 관리]");
                                        System.out.println("    [케이크 재고 현황]");
                                        String cakeListSQL = "SELECT cake_name, price, stock_quantity, state FROM cake";  //케이크 목록과 재고 현황을 조회하는 SQL
                                        PreparedStatement psCakeList = conn.prepareStatement(cakeListSQL);
                                        ResultSet rsCakeList = psCakeList.executeQuery();
                                        while (rsCakeList.next()) {
                                            System.out.println("- " + rsCakeList.getString("cake_name") +
                                                    " (" + rsCakeList.getInt("price") + "원, 재고: " + rsCakeList.getInt("stock_quantity") + "개, 상태: " + rsCakeList.getString("state") + ")");
                                        }
                                        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.println("1. 신규 케이크 등록");
                                        System.out.println("2. 케이크 정보 수정 (재고, 가격, 상태)");
                                        System.out.println("3. 케이크 삭제 ");
                                        System.out.println("0. 뒤로가기");
                                        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.print("번호를 입력해주세요 >> ");
                                        int cakeMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (cakeMenu == 1) {
                                            System.out.println("\n📝 [관리자 메뉴] - [케이크 관리] - [신규 케이크 등록]");
                                            System.out.print("케이크 이름: ");
                                            String cakeName = sc.nextLine();
                                            System.out.print("가격: ");
                                            int price = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("재고 수량: ");
                                            int stock = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("크기: ");
                                            String size = sc.nextLine();
                                            System.out.print("색상: ");
                                            String color = sc.nextLine();
                                            System.out.print("상태 (판매중/품절): ");
                                            String state = sc.nextLine();

                                            // 케이크 등록 SQL: 새로운 케이크 추가
                                            String insertCakeSQL = "INSERT INTO cake (cake_name, price, stock_quantity, size, color, state) VALUES (?, ?, ?, ?, ?, ?)";
                                            PreparedStatement psInsertCake = conn.prepareStatement(insertCakeSQL);
                                            psInsertCake.setString(1, cakeName.trim());
                                            psInsertCake.setInt(2, price);
                                            psInsertCake.setInt(3, stock);
                                            psInsertCake.setString(4, size);
                                            psInsertCake.setString(5, color);
                                            psInsertCake.setString(6, state);
                                            int result = psInsertCake.executeUpdate();

                                            if (result > 0) {
                                                System.out.println("✅ 케이크 등록 완료!");
                                            } else {
                                                System.out.println("❌ 케이크 등록 실패...");
                                            }

                                        } else if (cakeMenu == 2) {
                                            System.out.println("[관리자 메뉴] - [케이크 관리] - [케이크 정보 수정]");
                                            System.out.print("수정할 케이크 이름을 입력하세요: ");
                                            String cakeName = sc.nextLine().trim();

                                            // 입력한 케이크 이름에 해당하는 케이크의 ID, 가격, 재고, 상태 조회
                                            // → 케이크 정보 수정 또는 주문 시 해당 케이크의 현재 정보를 불러오기 위해 사용
                                            String cakeCheckSQL = "SELECT cake_id, price, stock_quantity, state FROM cake WHERE cake_name = ?";
                                            PreparedStatement psCheck = conn.prepareStatement(cakeCheckSQL);
                                            psCheck.setString(1, cakeName);
                                            ResultSet rsCheck = psCheck.executeQuery();

                                            if (!rsCheck.next()) {
                                                System.out.println("❌ 해당 케이크가 존재하지 않습니다.");
                                                break;
                                            }

                                            int cakeId = rsCheck.getInt("cake_id");
                                            int currentPrice = rsCheck.getInt("price");
                                            int currentStock = rsCheck.getInt("stock_quantity");
                                            String currentState = rsCheck.getString("state");

                                            System.out.println("현재 정보: 가격(" + currentPrice + "원), 재고(" + currentStock + "개), 상태(" + currentState + ")");
                                            System.out.print("새 가격 입력 (그대로 두려면 Enter): ");
                                            String newPriceInput = sc.nextLine();
                                            int newPrice = newPriceInput.isEmpty() ? currentPrice : Integer.parseInt(newPriceInput);

                                            System.out.print("새 재고 입력 (그대로 두려면 Enter): ");
                                            String newStockInput = sc.nextLine();
                                            int newStock = newStockInput.isEmpty() ? currentStock : Integer.parseInt(newStockInput);

                                            System.out.print("새 상태 입력 (그대로 두려면 Enter, 판매중/품절): ");
                                            String newStateInput = sc.nextLine();
                                            String newState = newStateInput.isEmpty() ? currentState : newStateInput;

                                            conn.setAutoCommit(false);  // 트랜잭션 시작
                                            try {
                                                // 케이크 수정 SQL: 가격, 재고, 상태 업데이트
                                                String updateCakeSQL = "UPDATE cake SET price = ?, stock_quantity = ?, state = ? WHERE cake_id = ?";
                                                PreparedStatement psUpdate = conn.prepareStatement(updateCakeSQL);
                                                psUpdate.setInt(1, newPrice);
                                                psUpdate.setInt(2, newStock);
                                                psUpdate.setString(3, newState);
                                                psUpdate.setInt(4, cakeId);

                                                int rowsUpdated = psUpdate.executeUpdate();

                                                if (rowsUpdated > 0) {
                                                    conn.commit();  // 성공 시 커밋
                                                    System.out.println("✅ 케이크 정보가 성공적으로 수정되었습니다!");
                                                } else {
                                                    conn.rollback();  // 실패 시 롤백
                                                    System.out.println("❌ 수정 실패. 다시 시도해주세요.");
                                                }
                                            } catch (Exception e) {
                                                conn.rollback();  // 예외 발생 시 롤백
                                                System.out.println("❌ 수정 중 오류 발생. 롤백되었습니다.");
                                                e.printStackTrace();
                                            } finally {
                                                conn.setAutoCommit(true);  // 트랜잭션 종료 후 자동 커밋 복원
                                            }

                                        } else if (cakeMenu == 3) {
                                            System.out.println("[관리자 메뉴] - [케이크 관리] - [케이크 삭제]");
                                            System.out.print("삭제할 케이크 이름을 입력하세요: ");
                                            String deleteCakeName = sc.nextLine().trim();
                                            // 상태가 '품절'인 케이크 중 삭제할 이름에 해당하는 케이크의 ID 조회
                                            String getCakeIdSQL = "SELECT cake_id FROM cake WHERE cake_name = ? AND state = '품절'";
                                            PreparedStatement psGetCakeId = conn.prepareStatement(getCakeIdSQL);
                                            psGetCakeId.setString(1, deleteCakeName);
                                            ResultSet rsCakeId = psGetCakeId.executeQuery();

                                            if (rsCakeId.next()) {
                                                int cakeId = rsCakeId.getInt("cake_id");

                                                // 케이크 삭제 SQL: 주문 항목 테이블에서 해당 케이크 ID 참조하는 레코드 삭제
                                                String deleteOrderItemSQL = "DELETE FROM orderitem WHERE cake_id = ?";
                                                PreparedStatement psDeleteOrderItem = conn.prepareStatement(deleteOrderItemSQL);
                                                psDeleteOrderItem.setInt(1, cakeId);
                                                psDeleteOrderItem.executeUpdate();
                                                //케이크 테이블에서 해당 케이크 삭제
                                                String deleteCakeSQL = "DELETE FROM cake WHERE cake_id = ?";
                                                PreparedStatement psDeleteCake = conn.prepareStatement(deleteCakeSQL);
                                                psDeleteCake.setInt(1, cakeId);
                                                int deletedRows = psDeleteCake.executeUpdate();
                                                // 삭제 결과 출력
                                                if (deletedRows > 0) {
                                                    System.out.println("✅ 케이크와 관련 주문 항목이 성공적으로 삭제되었습니다.");
                                                } else {
                                                    System.out.println("❌ 삭제 실패: 조건에 맞는 케이크가 없습니다.");
                                                }
                                            } else {
                                                System.out.println("❌ 삭제 실패: 조건에 맞는 케이크가 없습니다.");
                                            }
                                        } else if (cakeMenu == 0) {
                                            break;
                                        }
                                        else {
                                            System.out.println("❌ 잘못된 입력입니다.");
                                        }
                                    }


                                } else if (adminMenu == 2) {
                                    while(true){
                                        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.println("📋 [관리자 메뉴] - [주문 관리]");
                                        System.out.println("1. 전체 주문 내역 확인");
                                        System.out.println("2. 수령 날짜별 주문 내역 확인");
                                        System.out.println("0. 뒤로가기");
                                        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.print("번호를 입력해주세요 >> ");
                                        int orderMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (orderMenu == 1) {
                                            System.out.println("\n📋 [관리자 메뉴] - [주문 관리] - [전체 주문 내역 보기]");
                                            // 모든 주문 정보를 주문번호 기준으로 정렬하여 조회 (관리자가 전체 주문 확인 시 사용)
                                            String allOrdersSQL = "SELECT order_id, user_id, order_date, pickup_date, tot_price, request FROM orders ORDER BY order_id ASC";
                                            PreparedStatement psAllOrders = conn.prepareStatement(allOrdersSQL);
                                            ResultSet rsAllOrders = psAllOrders.executeQuery();

                                            boolean found = false;
                                            while (rsAllOrders.next()) {
                                                found = true;
                                                System.out.println("──────────────");
                                                System.out.println("🍒 주문번호: " + rsAllOrders.getInt("order_id"));
                                                System.out.println("- 사용자ID: " + rsAllOrders.getInt("user_id"));
                                                System.out.println("- 주문일자: " + rsAllOrders.getString("order_date"));
                                                System.out.println("- 수령일자: " + rsAllOrders.getString("pickup_date"));
                                                System.out.println("- 총 금액: " + rsAllOrders.getInt("tot_price") + "원");
                                                System.out.println("- 요청사항: " + rsAllOrders.getString("request"));
                                            }

                                            if (!found) {
                                                System.out.println("📭 등록된 주문 내역이 없습니다.");
                                            } else {
                                                System.out.println("---------------------");
                                                System.out.print("\n상세 주문 내역을 확인할 주문번호(들)를 입력하세요 (공백 구분, Enter로 건너뛰기): ");
                                                String input = sc.nextLine().trim();
                                                if (!input.isEmpty()) {
                                                    String[] orderIds = input.split("\\s+");
                                                    for (String idStr : orderIds) {
                                                        try {
                                                            int detailOrderId = Integer.parseInt(idStr);
                                                            // 주문 상세 내역 SQL: 특정 주문번호의 상세 정보 조회
                                                            String detailSQL = "SELECT v.order_id, v.order_date, v.pickup_date, v.cake_name, v.quantity, v.cake_total, " +
                                                                    "v.item_request, p.payment_method, p.payment_date " +
                                                                    "FROM order_detail_view v JOIN payment p ON v.order_id = p.order_id WHERE v.order_id = ?";
                                                            PreparedStatement psDetail = conn.prepareStatement(detailSQL);
                                                            psDetail.setInt(1, detailOrderId);
                                                            ResultSet rsDetail = psDetail.executeQuery();

                                                            if (rsDetail.next()) {
                                                                System.out.println("\n──────────────");
                                                                System.out.println("🍓 주문번호: " + rsDetail.getInt("order_id"));
                                                                System.out.println("- 주문일: " + rsDetail.getString("order_date"));
                                                                System.out.println("- 수령일: " + rsDetail.getString("pickup_date"));
                                                                System.out.println("- 케이크: " + rsDetail.getString("cake_name"));
                                                                System.out.println("- 수량: " + rsDetail.getInt("quantity"));
                                                                System.out.println("- 금액: " + rsDetail.getInt("cake_total") + "원");
                                                                System.out.println("- 요청사항: " + rsDetail.getString("item_request"));
                                                                System.out.println("- 결제수단: " + rsDetail.getString("payment_method"));
                                                                System.out.println("- 결제일시: " + rsDetail.getString("payment_date"));
                                                            } else {
                                                                System.out.println("❌ 주문번호 " + detailOrderId + "에 대한 상세 내역이 없습니다.");
                                                            }

                                                            rsDetail.close();
                                                            psDetail.close();
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("❌ 잘못된 주문번호 형식: " + idStr);
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (orderMenu == 2) {
                                            System.out.println("[관리자 메뉴] - [주문 관리] - [수령 날짜별 주문 내역 확인]");
                                            System.out.print("\n📅 확인할 날짜를 입력하세요 (YYYY-MM-DD): ");
                                            String pickupDate = sc.nextLine().trim();

                                            // 수령일 조건 주문 조회 SQL
                                            String todayOrdersSQL = "SELECT order_id, user_id, order_date, pickup_date, tot_price, request " +
                                                    "FROM orders WHERE DATE(pickup_date) = ? ORDER BY order_id ASC";

                                            PreparedStatement psTodayOrders = conn.prepareStatement(todayOrdersSQL);
                                            psTodayOrders.setString(1, pickupDate);
                                            ResultSet rsTodayOrders = psTodayOrders.executeQuery();

                                            boolean found = false;
                                            while (rsTodayOrders.next()) {
                                                found = true;
                                                System.out.println("──────────────");
                                                System.out.println("🍒 주문번호: " + rsTodayOrders.getInt("order_id"));
                                                System.out.println("- 사용자ID: " + rsTodayOrders.getInt("user_id"));
                                                System.out.println("- 주문일자: " + rsTodayOrders.getString("order_date"));
                                                System.out.println("- 수령일자: " + rsTodayOrders.getString("pickup_date"));
                                                System.out.println("- 총 금액: " + rsTodayOrders.getInt("tot_price") + "원");
                                                System.out.println("- 요청사항: " + rsTodayOrders.getString("request"));
                                            }

                                            if (!found) {
                                                System.out.println("📭 해당 날짜에 준비할 케이크가 없습니다.");
                                            }
                                        }
                                        else if (orderMenu == 0) {
                                            break;
                                        }
                                        else {
                                            System.out.println("❌ 잘못된 입력입니다.");
                                        }
                                    }

                                } else if (adminMenu == 3) {
                                    while(true){
                                        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.println("📊 [관리자 메뉴] - [가게 관리]");
                                        System.out.println("1. 케이크별 매출액 조회");
                                        System.out.println("2. 고객별 누적 주문 총액");
                                        System.out.println("0. 뒤로가기");
                                        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.print("번호를 입력해주세요 >> ");
                                        int storeMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (storeMenu == 1) {
                                            System.out.println("\n💰 [관리자 메뉴] - [가게 관리] - [케이크별 총 매출액 보기]");
                                            // 케이크별 매출 집계 SQL
                                            String sql = "SELECT c.cake_name, SUM(i.tot_price) AS total_sales " +
                                                    "FROM orderitem i " +
                                                    "JOIN cake c ON i.cake_id = c.cake_id " +
                                                    "GROUP BY c.cake_id " +
                                                    "ORDER BY total_sales DESC";

                                            PreparedStatement pstmt = conn.prepareStatement(sql);
                                            ResultSet rs = pstmt.executeQuery();

                                            while (rs.next()) {
                                                String cakeName = rs.getString("cake_name");
                                                int totalSales = rs.getInt("total_sales");
                                                System.out.printf("%s: %d원\n", cakeName, totalSales);
                                            }
                                        } else if (storeMenu == 2) {
                                            System.out.println("\n📊 [관리자 메뉴] - [가게 관리] - [고객 별 누적 주문 총액 보기]");
                                            System.out.print("조회할 고객 이름을 입력하세요: ");
                                            String customerName = sc.nextLine();

                                            // 고객별 누적 금액 조회 SQL
                                            String query = "SELECT u.user_name, SUM(o.tot_price) AS total_amount " +
                                                    "FROM user u JOIN orders o ON u.user_id = o.user_id " +
                                                    "WHERE u.user_name = ? " +
                                                    "GROUP BY u.user_id, u.user_name";

                                            PreparedStatement ps = conn.prepareStatement(query);
                                            ps.setString(1, customerName);
                                            ResultSet rs = ps.executeQuery();

                                            if (rs.next()) {
                                                System.out.println("──────────────");
                                                System.out.println("고객명: " + rs.getString("user_name"));
                                                System.out.println("총 주문 금액: " + rs.getInt("total_amount") + "원");
                                            } else {
                                                System.out.println("❌ 해당 고객의 주문 내역이 없습니다.");
                                            }
                                        }
                                        else if (storeMenu == 0) {
                                            break;
                                        }
                                    }
                                }
                                else if (adminMenu == 0) {
                                    System.out.println("로그아웃합니다.");
                                    break;
                                } else {
                                    System.out.println("잘못된 입력입니다.");
                                }
                            }
                        }else { // 일반 사용자
                            while (true) {
                                System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                System.out.println("🎂 [메인 메뉴]");
                                System.out.println("1. 케이크 주문");
                                System.out.println("2. 내 주문 관리");
                                System.out.println("3. 인기 케이크 TOP 3");
                                System.out.println("0. 로그아웃");
                                System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                System.out.print("번호를 입력해주세요 >> ");
                                int menu = sc.nextInt();
                                sc.nextLine();

                                if (menu == 1) {
                                    System.out.println("\n📋 [메인 메뉴] - [케이크 주문하기] - [구매 가능한 케이크 목록]");
                                    // available_cake_view를 통해 구매 가능한 케이크만 조회
                                    String cakeListSQL = "SELECT cake_name, price, stock_quantity, state FROM available_cake_view";
                                    PreparedStatement psCakeList = conn.prepareStatement(cakeListSQL);
                                    ResultSet rsCakeList = psCakeList.executeQuery();


                                    boolean hasAvailableCake = false; // 구매 가능한 케이크 있는지 확인
                                    while (rsCakeList.next()) {
                                        hasAvailableCake = true;
                                        System.out.println("- " + rsCakeList.getString("cake_name") +
                                                " (" + rsCakeList.getInt("price") + "원, 재고: " + rsCakeList.getInt("stock_quantity") + "개 )");
                                    }

                                    if (!hasAvailableCake) {
                                        System.out.println("\n ❌ 현재 모든 케이크가 품절인 상황입니다. 주문을 원하시는 경우, 가게에 직접 문의 부탁드립니다.");
                                        continue; // 주문 프로세스 종료
                                    }

                                    // 구매 가능한 케이크가 있다면, 기존 코드 진행
                                    System.out.print("\n케이크 종류를 선택하세요: ");
                                    String cakeName = sc.nextLine().trim();

                                    System.out.print("수량을 입력하세요: ");
                                    int quantity = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("수령 날짜를 입력하세요 (YYYY-MM-DD): ");
                                    String pickupDate = sc.nextLine();
                                    System.out.print("요청사항: ");
                                    String request = sc.nextLine();
                                    Timestamp orderDate = Timestamp.valueOf(LocalDateTime.now());

                                    if (LocalDate.parse(pickupDate).isBefore(LocalDate.now())) {
                                        System.out.println("❌ 수령일은 오늘 이후 날짜로만 선택 가능합니다.");
                                        continue; // 주문 진행 중단
                                    }

                                    // 케이크 정보 가져오기
                                    String cakeQuery = "SELECT cake_id, price, stock_quantity FROM cake WHERE TRIM(cake_name) = ?";
                                    PreparedStatement psCake = conn.prepareStatement(cakeQuery);
                                    psCake.setString(1, cakeName);
                                    ResultSet rsCake = psCake.executeQuery();

                                    if (!rsCake.next()) {
                                        System.out.println("❌ 해당 케이크가 존재하지 않습니다.");
                                        continue;
                                    }

                                    int cakeId = rsCake.getInt("cake_id");
                                    int price = rsCake.getInt("price");
                                    int stockQuantity = rsCake.getInt("stock_quantity");

                                    if (quantity > stockQuantity) {
                                        System.out.println("❌ 재고 부족! 현재 재고: " + stockQuantity + "개");
                                        continue;
                                    }

                                    int totalPrice = price * quantity;

                                    // 주문 정보 INSERT
                                    String insertOrderSQL = "INSERT INTO orders (user_id, order_date, pickup_date, tot_price, request) VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
                                    psOrder.setInt(1, userId);
                                    psOrder.setTimestamp(2, orderDate);
                                    psOrder.setString(3, pickupDate);
                                    psOrder.setInt(4, totalPrice);
                                    psOrder.setString(5, request);
                                    psOrder.executeUpdate();

                                    ResultSet rsOrderId = psOrder.getGeneratedKeys();
                                    int orderId = -1;
                                    if (rsOrderId.next()) {
                                        orderId = rsOrderId.getInt(1);
                                    }

                                    // 주문 아이템 INSERT
                                    String insertOrderItemSQL = "INSERT INTO orderitem (order_id, cake_id, quantity, tot_price, request) VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement psOrderItem = conn.prepareStatement(insertOrderItemSQL);
                                    psOrderItem.setInt(1, orderId);
                                    psOrderItem.setInt(2, cakeId);
                                    psOrderItem.setInt(3, quantity);
                                    psOrderItem.setInt(4, totalPrice);
                                    psOrderItem.setString(5, request);
                                    psOrderItem.executeUpdate();

                                    conn.setAutoCommit(false);
                                    try {
                                        // 케이크 재고 차감 SQL (주문 시)
                                        String updateStockSQL = "UPDATE cake SET stock_quantity = stock_quantity - ? WHERE cake_id = ?";
                                        PreparedStatement psUpdateStock = conn.prepareStatement(updateStockSQL);
                                        psUpdateStock.setInt(1, quantity);
                                        psUpdateStock.setInt(2, cakeId);
                                        psUpdateStock.executeUpdate();

                                        // 케이크 상태를 품절로 변경
                                        String updateStateSQL = "UPDATE cake SET state = '품절' WHERE cake_id = ? AND stock_quantity <= 0";
                                        PreparedStatement psUpdateState = conn.prepareStatement(updateStateSQL);
                                        psUpdateState.setInt(1, cakeId);
                                        psUpdateState.executeUpdate();

                                        conn.commit();  // 모든 update가 정상적으로 끝났을 경우 커밋
                                    } catch (Exception e) {
                                        conn.rollback(); // 하나라도 실패하면 롤백
                                        System.out.println("❌ 주문 처리 중 오류 발생. 롤백되었습니다.");
                                    }
                                    conn.setAutoCommit(true); // 트랜잭션 끝나고 자동 커밋 다시 켜기


                                    // 결제 처리
                                    System.out.println("--------------------");
                                    System.out.println("총 결제 금액은 " + totalPrice + "원입니다.");
                                    System.out.print("결제 수단을 선택하세요 (card / transfer): ");
                                    String method = sc.nextLine();
                                    int payout = totalPrice;
                                    // 결제 정보 INSERT
                                    String paymentSQL = "INSERT INTO payment (order_id, tot_price, payout, payment_method, payment_date) VALUES (?, ?, ?, ?, NOW())";
                                    PreparedStatement psPayment = conn.prepareStatement(paymentSQL);
                                    psPayment.setInt(1, orderId);
                                    psPayment.setInt(2, totalPrice);
                                    psPayment.setInt(3, payout);
                                    psPayment.setString(4, method);
                                    psPayment.executeUpdate();

                                    System.out.println("✅ 주문과 결제가 완료되었습니다!");
                                    System.out.println("──────────────");
                                    System.out.println("🍰 주문번호: " + orderId);
                                    System.out.println("- 케이크: " + cakeName);
                                    System.out.println("- 수량: " + quantity + "개");
                                    System.out.println("- 총 결제 금액: " + totalPrice + "원");
                                    System.out.println("- 결제 수단: " + method);
                                    System.out.println("- 수령 날짜: " + pickupDate);
                                    System.out.println("- 요청사항: " + (request.isEmpty() ? "없음" : request));
                                    System.out.println("⚠️ 픽업 시 주문번호 또는 성함을 말씀해주세요.");
                                }

                                else if (menu == 2) {
                                    while (true) {
                                        System.out.println("\n👤 [메인 메뉴] - [내 주문 관리]");
                                        System.out.println("1. 주문 상세 조회");
                                        System.out.println("2. 주문 내역 전체 보기");
                                        System.out.println("3. 주문 수정/취소");
                                        System.out.println("0. 뒤로가기");
                                        System.out.print("번호를 입력해주세요 >> ");
                                        int orderMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (orderMenu == 1) {
                                            System.out.println("\n🧾 [메인 메뉴] - [내 주문 관리] - [상세 주문 내역 보기]");
                                            System.out.print("조회할 주문 번호 입력하세요: ");
                                            int orderId = sc.nextInt();
                                            sc.nextLine();

                                            // 사용자 본인의 특정 주문 상세 내역 + 결제 정보까지 조회 (order_detail_view 뷰와 payment 테이블 조인)
                                            String detailSQL = "SELECT v.order_id, v.order_date, v.pickup_date, v.cake_name, v.quantity, v.cake_total, " +
                                                    "v.item_request, p.payment_method, p.payment_date FROM order_detail_view v " +
                                                    "JOIN payment p ON v.order_id = p.order_id WHERE v.order_id = ? AND v.user_id = ?";
                                            PreparedStatement psDetail = conn.prepareStatement(detailSQL);
                                            psDetail.setInt(1, orderId);
                                            psDetail.setInt(2, userId);
                                            ResultSet rs = psDetail.executeQuery();

                                            if (rs.next()) {
                                                System.out.println("──────────────");
                                                System.out.println("🍓 주문번호: " + rs.getInt("order_id"));
                                                System.out.println("- 주문일: " + rs.getString("order_date"));
                                                System.out.println("- 수령일: " + rs.getString("pickup_date"));
                                                System.out.println("- 케이크: " + rs.getString("cake_name"));
                                                System.out.println("- 수량: " + rs.getInt("quantity"));
                                                System.out.println("- 금액: " + rs.getInt("cake_total") + "원");
                                                System.out.println("- 요청사항: " + rs.getString("item_request"));
                                                System.out.println("- 결제수단: " + rs.getString("payment_method"));
                                                System.out.println("- 결제일시: " + rs.getString("payment_date"));
                                            } else {
                                                System.out.println("❌ 해당 주문 내역이 없습니다.");
                                            }


                                        }
                                        else if (orderMenu == 2) {
                                            System.out.println("\n👤 [메인 메뉴] - [내 주문 관리] -[주문 내역 전체 보기]");
                                            // 로그인한 사용자의 모든 주문 내역 조회 (주문번호, 케이크 이름, 주문일)
                                            // 최근 주문일 순으로 내림차순 정렬
                                            String infoSQL = "SELECT o.order_id, c.cake_name, o.order_date FROM orders o " +
                                                    "JOIN orderitem i ON o.order_id = i.order_id " +
                                                    "JOIN cake c ON i.cake_id = c.cake_id WHERE o.user_id = ? ORDER BY o.order_date DESC";
                                            PreparedStatement psInfo = conn.prepareStatement(infoSQL);
                                            psInfo.setInt(1, userId);
                                            ResultSet rsInfo = psInfo.executeQuery();

                                            boolean found = false;
                                            while (rsInfo.next()) {
                                                found = true;
                                                System.out.println("──────────────");
                                                System.out.println("🍓 주문번호: " + rsInfo.getInt("order_id"));
                                                System.out.println("- 케이크: " + rsInfo.getString("cake_name"));
                                                System.out.println("- 주문일: " + rsInfo.getString("order_date"));
                                            }
                                            if (!found) System.out.println("✅ 내 주문 내역이 없습니다.");
                                        }
                                        else if (orderMenu == 3) {
                                            System.out.println("\n🧾 [메인 메뉴] - [내 주문 관리] -[주문 수정/취소]");
                                            System.out.println("1. 주문 수정하기");
                                            System.out.println("2. 주문 취소하기");
                                            System.out.print("번호를 입력해주세요 >> ");
                                            int subMenu = sc.nextInt();
                                            sc.nextLine();

                                            if (subMenu == 1) {

                                                // 사용자의 주문 내역이 있는지 먼저 확인
                                                String userOrderCheckSQL = "SELECT COUNT(*) FROM orders WHERE user_id = ?";
                                                PreparedStatement psUserOrderCheck = conn.prepareStatement(userOrderCheckSQL);
                                                psUserOrderCheck.setInt(1, userId);
                                                ResultSet rsUserOrderCheck = psUserOrderCheck.executeQuery();

                                                if (rsUserOrderCheck.next() && rsUserOrderCheck.getInt(1) == 0) {
                                                    System.out.println("❌ 아직 주문 내역이 없습니다. 주문 후 이용해주세요.");
                                                    continue;
                                                }

                                                System.out.print("수정할 주문 번호를 입력하세요: ");
                                                int orderId = sc.nextInt();
                                                sc.nextLine();

                                                // 수령일이 이미 지난 경우 수정 불가
                                                String getPickupDateSQL = "SELECT pickup_date FROM orders WHERE order_id = ?";
                                                PreparedStatement psPickup = conn.prepareStatement(getPickupDateSQL);
                                                psPickup.setInt(1, orderId);
                                                ResultSet rsPickup = psPickup.executeQuery();

                                                if (rsPickup.next()) {
                                                    String pickupDate = rsPickup.getString("pickup_date");
                                                    if (LocalDate.parse(pickupDate).isBefore(LocalDate.now())) {
                                                        System.out.println("❌ 수령일이 지난 주문은 수정할 수 없습니다.");
                                                        continue;
                                                    }
                                                }


                                                // 입력한 주문번호에 해당하는 주문의 사용자 ID, 수령일, 요청사항 조회
                                                // → 주문 수정 또는 취소 시 본인 여부 확인과 기존 데이터 표시를 위해 사용
                                                String checkOrderSQL = "SELECT user_id, pickup_date, request FROM orders WHERE order_id = ?";
                                                PreparedStatement psCheckOrder = conn.prepareStatement(checkOrderSQL);
                                                psCheckOrder.setInt(1, orderId);
                                                ResultSet rsCheck = psCheckOrder.executeQuery();

                                                if (!rsCheck.next()) {
                                                    System.out.println("❌ 해당 주문이 존재하지 않습니다.");
                                                    continue;
                                                }

                                                int orderUserId = rsCheck.getInt("user_id");
                                                if (orderUserId != userId) {
                                                    System.out.println("❌ 본인의 주문만 수정할 수 있습니다.");
                                                    continue;
                                                }
                                                System.out.println("--------------------");
                                                // 현재 정보 표시
                                                String currentPickup = rsCheck.getString("pickup_date");

                                                String currentRequest = rsCheck.getString("request");
                                                System.out.println("현재 수령일: " + currentPickup);
                                                // 새 정보 입력
                                                System.out.print(">> 새 수령일 입력(YYYY-MM-DD) (Enter 시 기존 유지): ");
                                                String newPickupDate = sc.nextLine();

                                                // ✅ 빈 문자열 체크 후 parse 실행
                                                if (!newPickupDate.isEmpty() && LocalDate.parse(newPickupDate).isBefore(LocalDate.now())) {
                                                    System.out.println("❌ 오늘 이전의 날짜로는 수령일을 변경할 수 없습니다.");
                                                    continue;
                                                }

                                                if (newPickupDate.isEmpty()) newPickupDate = currentPickup;

                                                System.out.println("현재 요청사항: " + currentRequest);
                                                System.out.print(">> 새 요청사항 입력 (Enter 시 기존 유지): ");
                                                String newRequest = sc.nextLine();
                                                if (newRequest.isEmpty()) newRequest = currentRequest;


                                                // 주문 수정 SQL (orders 테이블)
                                                String updateOrdersSQL = "UPDATE orders SET pickup_date = ?, request = ? WHERE order_id = ?";
                                                PreparedStatement psUpdateOrders = conn.prepareStatement(updateOrdersSQL);
                                                psUpdateOrders.setString(1, newPickupDate);
                                                psUpdateOrders.setString(2, newRequest);
                                                psUpdateOrders.setInt(3, orderId);
                                                int rowsUpdated = psUpdateOrders.executeUpdate();

                                                // 주문 수정 SQL (orderitem 테이블 요청사항 동기화)
                                                String updateOrderItemSQL = "UPDATE orderitem SET request = ? WHERE order_id = ?";
                                                PreparedStatement psUpdateOrderItem = conn.prepareStatement(updateOrderItemSQL);
                                                psUpdateOrderItem.setString(1, newRequest);
                                                psUpdateOrderItem.setInt(2, orderId);
                                                int itemUpdated = psUpdateOrderItem.executeUpdate();

                                                if (rowsUpdated > 0 || itemUpdated > 0) {
                                                    System.out.println("✅ 주문 정보가 성공적으로 수정되었습니다.");
                                                } else {
                                                    System.out.println("❌ 수정 실패: 주문 정보가 변경되지 않았습니다.");
                                                }
                                            }

                                            else if (subMenu == 2) {
                                                System.out.println("[메인 메뉴] - [내 주문 관리] - [주문 수정/취소]");
                                                System.out.print("취소할 주문 번호를 입력하세요: ");
                                                int orderId = sc.nextInt();
                                                sc.nextLine();

                                                // 본인 주문인지 확인
                                                String checkOrderSQL = "SELECT user_id FROM orders WHERE order_id = ?";
                                                PreparedStatement psCheckOrder = conn.prepareStatement(checkOrderSQL);
                                                psCheckOrder.setInt(1, orderId);
                                                ResultSet rsCheck = psCheckOrder.executeQuery();

                                                if (!rsCheck.next()) {
                                                    System.out.println("❌ 해당 주문이 존재하지 않습니다.");
                                                    continue;
                                                }

                                                int orderUserId = rsCheck.getInt("user_id");
                                                if (orderUserId != userId) {
                                                    System.out.println("❌ 본인의 주문만 취소할 수 있습니다.");
                                                    continue;
                                                }
                                                // 수령일이 지난 주문은 취소할 수 없음
                                                String pickupSQL = "SELECT pickup_date FROM orders WHERE order_id = ?";
                                                PreparedStatement psPickup = conn.prepareStatement(pickupSQL);
                                                psPickup.setInt(1, orderId);
                                                ResultSet rsPickup = psPickup.executeQuery();

                                                if (rsPickup.next()) {
                                                    String pickupDate = rsPickup.getString("pickup_date");
                                                    if (LocalDate.parse(pickupDate).isBefore(LocalDate.now())) {
                                                        System.out.println("❌ 수령일이 지난 주문은 취소할 수 없습니다.");
                                                        continue;
                                                    }
                                                }


                                                // 주문한 케이크 ID와 수량 조회
                                                String selectItemsSQL = "SELECT cake_id, quantity FROM orderitem WHERE order_id = ?";
                                                PreparedStatement psSelectItems = conn.prepareStatement(selectItemsSQL);
                                                psSelectItems.setInt(1, orderId);
                                                ResultSet rsItems = psSelectItems.executeQuery();

                                                while (rsItems.next()) {
                                                    int cakeId = rsItems.getInt("cake_id");
                                                    int qty = rsItems.getInt("quantity");

                                                    // 주문 취소 시 재고 복구 SQL
                                                    String updateStockSQL = "UPDATE cake SET stock_quantity = stock_quantity + ? WHERE cake_id = ?";
                                                    PreparedStatement psRestoreStock = conn.prepareStatement(updateStockSQL);
                                                    psRestoreStock.setInt(1, qty);
                                                    psRestoreStock.setInt(2, cakeId);
                                                    psRestoreStock.executeUpdate();

                                                    // 주문 취소 시 상태 복구 SQL (판매중으로 변경)
                                                    String updateStateSQL = "UPDATE cake SET state = '판매중' WHERE cake_id = ? AND stock_quantity > 0";
                                                    PreparedStatement psUpdateState = conn.prepareStatement(updateStateSQL);
                                                    psUpdateState.setInt(1, cakeId);
                                                    psUpdateState.executeUpdate();
                                                }


                                                // 주문 취소: orderitem 삭제
                                                String deleteOrderItemSQL = "DELETE FROM orderitem WHERE order_id = ?";
                                                PreparedStatement psDeleteOrderItem = conn.prepareStatement(deleteOrderItemSQL);
                                                psDeleteOrderItem.setInt(1, orderId);
                                                psDeleteOrderItem.executeUpdate();

                                                // 주문 취소: payment 삭제
                                                String deletePaymentSQL = "DELETE FROM payment WHERE order_id = ?";
                                                PreparedStatement psDeletePayment = conn.prepareStatement(deletePaymentSQL);
                                                psDeletePayment.setInt(1, orderId);
                                                psDeletePayment.executeUpdate();

                                                // 주문 취소: orders 삭제
                                                String deleteOrderSQL = "DELETE FROM orders WHERE order_id = ?";
                                                PreparedStatement psDeleteOrder = conn.prepareStatement(deleteOrderSQL);
                                                psDeleteOrder.setInt(1, orderId);
                                                int deleted = psDeleteOrder.executeUpdate();

                                                if (deleted > 0) {
                                                    System.out.println("✅ 주문이 취소되었습니다.");
                                                } else {
                                                    System.out.println("❌ 주문 취소 실패. 다시 시도해주세요.");
                                                }
                                            }
                                        }
                                        else if (orderMenu == 0) {
                                            break;
                                        }
                                        else {
                                            System.out.println("잘못된 입력입니다.");
                                        }
                                    }
                                }
                                else if (menu == 3) {
                                    System.out.println("\n🏆 [메인 메뉴] - [인기 케이크 TOP 3]");
                                    // 케이크별 주문 수량을 집계하여 상위 3개의 인기 케이크 이름 조회 (수량 기준 내림차순 정렬)
                                    String sql = "SELECT c.cake_name FROM orders o " +
                                            "JOIN orderitem i ON o.order_id = i.order_id " +
                                            "JOIN cake c ON i.cake_id = c.cake_id " +
                                            "GROUP BY c.cake_id ORDER BY SUM(i.quantity) DESC LIMIT 3";
                                    PreparedStatement pstmt = conn.prepareStatement(sql);
                                    ResultSet rs = pstmt.executeQuery();
                                    int rank = 1;
                                    while (rs.next()) {
                                        System.out.printf("%d위: %s\n", rank++, rs.getString("cake_name"));
                                    }
                                    if (rank == 1) System.out.println("📭 이번 달 판매된 케이크가 없습니다.");

                                }
                                else if (menu == 0) {
                                    System.out.println("로그아웃합니다.");
                                    break;
                                }
                                else {
                                    System.out.println("잘못된 입력입니다.");
                                }
                            }
                        }


                    } else {
                        System.out.println("❌ 로그인 실패! 아이디와 비밀번호를 확인해주세요.");
                    }

                } else if (choice == 2) {
                    // 회원가입 처리
                    // INSERT INTO `user` (login_id, pw, user_name, phone_number) ...
                    // 회원 정보 입력 후 DB에 저장
                    System.out.println("\n📝 [회원가입]");
                    System.out.print("아이디를 입력하세요: ");
                    String newLoginId = sc.nextLine();
                    System.out.print("비밀번호를 입력하세요: ");
                    String newPw = sc.nextLine();
                    System.out.print("이름을 입력하세요: ");
                    String name = sc.nextLine();
                    System.out.print("전화번호를 입력하세요: ");
                    String phone = sc.nextLine();

                    // 회원가입 SQL: 사용자 정보 등록
                    String insertSQL = "INSERT INTO `user` (login_id, pw, user_name, phone_number) VALUES (?, ?, ?, ?)";
                    PreparedStatement psInsert = conn.prepareStatement(insertSQL);
                    psInsert.setString(1, newLoginId);
                    psInsert.setString(2, newPw);
                    psInsert.setString(3, name);
                    psInsert.setString(4, phone);
                    int result = psInsert.executeUpdate();

                    if (result > 0) {
                        System.out.println("✅ 회원가입 성공! 이제 로그인해주세요.");
                    } else {
                        System.out.println("❌ 회원가입 실패. 다시 시도해주세요.");
                    }

                } else if (choice == 0) {
                    // 시스템 종료
                    System.out.println("시스템을 종료합니다.");
                    break;
                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
