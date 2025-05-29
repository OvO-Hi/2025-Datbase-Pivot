import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://www.i-kina.co.kr:23406/ewha";
    private static final String USER = "ewha";
    private static final String PASSWORD = "ewhaPass2025##";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n┌──────────────────────────────────────────────────┐");
                System.out.println("    🍓🎂        피벗들의 케이크 구움터        🍰🍒   ");
                System.out.println("          맛있는 케이크와 함께하는 달콤한 하루!              ");
                System.out.println("└──────────────────────────────────────────────────┘");
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("0. 종료");
                System.out.print("선택 >> ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    System.out.print("아이디를 입력하세요: ");
                    String loginId = sc.nextLine();
                    System.out.print("비밀번호를 입력하세요: ");
                    String pw = sc.nextLine();

                    String loginSQL = "SELECT user_id, user_name, is_admin FROM `user` WHERE login_id = ? AND pw = ?";
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
                                System.out.print("선택 >> ");
                                int adminMenu = sc.nextInt();
                                sc.nextLine();

                                if (adminMenu == 1) {
                                    while(true){
                                        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.println("🎂 [관리자 메뉴] - [케이크 관리]");
                                        System.out.println("1. 신규 케이크 등록");
                                        System.out.println("2. 케이크 정보 수정 (재고, 가격, 상태)");
                                        System.out.println("3. 케이크 삭제 ");
                                        System.out.println("0. 뒤로가기");
                                        System.out.println("━━━━━━━━━━━━━━━━━━━━━");
                                        System.out.print("선택 >> ");
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

                                            String updateCakeSQL = "UPDATE cake SET price = ?, stock_quantity = ?, state = ? WHERE cake_id = ?";
                                            PreparedStatement psUpdate = conn.prepareStatement(updateCakeSQL);
                                            psUpdate.setInt(1, newPrice);
                                            psUpdate.setInt(2, newStock);
                                            psUpdate.setString(3, newState);
                                            psUpdate.setInt(4, cakeId);

                                            int rowsUpdated = psUpdate.executeUpdate();
                                            if (rowsUpdated > 0) {
                                                System.out.println("✅ 케이크 정보가 성공적으로 수정되었습니다!");
                                            } else {
                                                System.out.println("❌ 수정 실패. 다시 시도해주세요.");
                                            }
                                        } else if (cakeMenu == 3) {
                                            System.out.println("[관리자 메뉴] - [케이크 관리] - [케이크 삭제]");
                                            System.out.print("삭제할 케이크 이름을 입력하세요: ");
                                            String deleteCakeName = sc.nextLine().trim();

                                            String getCakeIdSQL = "SELECT cake_id FROM cake WHERE cake_name = ? AND state = '품절'";
                                            PreparedStatement psGetCakeId = conn.prepareStatement(getCakeIdSQL);
                                            psGetCakeId.setString(1, deleteCakeName);
                                            ResultSet rsCakeId = psGetCakeId.executeQuery();

                                            if (rsCakeId.next()) {
                                                int cakeId = rsCakeId.getInt("cake_id");

                                                String deleteOrderItemSQL = "DELETE FROM orderitem WHERE cake_id = ?";
                                                PreparedStatement psDeleteOrderItem = conn.prepareStatement(deleteOrderItemSQL);
                                                psDeleteOrderItem.setInt(1, cakeId);
                                                psDeleteOrderItem.executeUpdate();

                                                String deleteCakeSQL = "DELETE FROM cake WHERE cake_id = ?";
                                                PreparedStatement psDeleteCake = conn.prepareStatement(deleteCakeSQL);
                                                psDeleteCake.setInt(1, cakeId);
                                                int deletedRows = psDeleteCake.executeUpdate();

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
                                        System.out.print("선택 >> ");
                                        int orderMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (orderMenu == 1) {
                                            System.out.println("\n📋 [관리자 메뉴] - [주문 관리] - [전체 주문 내역 보기]");
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
                                            }
                                        } else if (orderMenu == 2) {
                                            System.out.println("[관리자 메뉴] - [주문 관리] - [수령 날짜별 주문 내역 확인]");
                                            System.out.print("\n📅 확인할 날짜를 입력하세요 (YYYY-MM-DD): ");
                                            String pickupDate = sc.nextLine().trim();

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
                                        System.out.print("선택 >> ");
                                        int storeMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (storeMenu == 1) {
                                            System.out.println("\n💰 [관리자 메뉴] - [가게 관리] - [케이크별 총 매출액 보기]");
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
                                            String groupBySQL = "SELECT o.user_id, u.user_name, SUM(o.tot_price) AS total_amount " +
                                                    "FROM orders o JOIN user u ON o.user_id = u.user_id " +
                                                    "GROUP BY o.user_id, u.user_name ORDER BY total_amount DESC";

                                            PreparedStatement psGroupBy = conn.prepareStatement(groupBySQL);
                                            ResultSet rsGroupBy = psGroupBy.executeQuery();

                                            System.out.printf("%-10s %-15s %-10s\n", "User ID", "Name", "Total (₩)");
                                            System.out.println("────────────────────────────────────────");
                                            while (rsGroupBy.next()) {
                                                int uid = rsGroupBy.getInt("user_id");
                                                String uname = rsGroupBy.getString("user_name");
                                                int total = rsGroupBy.getInt("total_amount");
                                                System.out.printf("%-10d %-15s %,10d\n", uid, uname, total);
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
                                System.out.println("\n🎂 [메인 메뉴]");
                                System.out.println("1. 케이크 주문");
                                System.out.println("2. 내 주문 관리");
                                System.out.println("3. 인기 케이크 TOP 3");
                                System.out.println("0. 로그아웃");
                                System.out.print("선택 >> ");
                                int menu = sc.nextInt();
                                sc.nextLine();

                                if (menu == 1) {
                                    System.out.println("\n📋 [메인 메뉴] - [케이크 주문하기] - [구매 가능한 케이크 목록]");
                                    String cakeListSQL = "SELECT cake_name, price, stock_quantity, state FROM cake WHERE state = '판매중'";
                                    PreparedStatement psCakeList = conn.prepareStatement(cakeListSQL);
                                    ResultSet rsCakeList = psCakeList.executeQuery();
                                    while (rsCakeList.next()) {
                                        System.out.println("- " + rsCakeList.getString("cake_name") +
                                                " (" + rsCakeList.getInt("price") + "원, 재고: " + rsCakeList.getInt("stock_quantity") + "개, 상태: " + rsCakeList.getString("state") + ")");
                                    }

                                    System.out.print("\n케이크 종류를 선택하세요: ");
                                    String cakeName = sc.nextLine().trim();
                                    System.out.print("수량을 입력하세요: ");
                                    int quantity = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("수령 날짜를 입력하세요 (YYYY-MM-DD): ");
                                    String pickupDate = sc.nextLine();
                                    System.out.print("요청사항: ");
                                    String request = sc.nextLine();
                                    String orderDate = LocalDate.now().toString();

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

                                    // 주문 저장
                                    String insertOrderSQL = "INSERT INTO orders (user_id, order_date, pickup_date, tot_price, request) VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
                                    psOrder.setInt(1, userId);
                                    psOrder.setString(2, orderDate);
                                    psOrder.setString(3, pickupDate);
                                    psOrder.setInt(4, totalPrice);
                                    psOrder.setString(5, request);
                                    psOrder.executeUpdate();

                                    ResultSet rsOrderId = psOrder.getGeneratedKeys();
                                    int orderId = -1;
                                    if (rsOrderId.next()) {
                                        orderId = rsOrderId.getInt(1);
                                    }

                                    // 주문 아이템 저장
                                    String insertOrderItemSQL = "INSERT INTO orderitem (order_id, cake_id, quantity, tot_price, request) VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement psOrderItem = conn.prepareStatement(insertOrderItemSQL);
                                    psOrderItem.setInt(1, orderId);
                                    psOrderItem.setInt(2, cakeId);
                                    psOrderItem.setInt(3, quantity);
                                    psOrderItem.setInt(4, totalPrice);
                                    psOrderItem.setString(5, request);
                                    psOrderItem.executeUpdate();

                                    // 케이크 재고 차감
                                    String updateStockSQL = "UPDATE cake SET stock_quantity = stock_quantity - ? WHERE cake_id = ?";
                                    PreparedStatement psUpdateStock = conn.prepareStatement(updateStockSQL);
                                    psUpdateStock.setInt(1, quantity);
                                    psUpdateStock.setInt(2, cakeId);
                                    psUpdateStock.executeUpdate();

                                    // 재고 0이면 상태 'sold out'으로 변경
                                    String updateStateSQL = "UPDATE cake SET state = '품절' WHERE cake_id = ? AND stock_quantity <= 0";
                                    PreparedStatement psUpdateState = conn.prepareStatement(updateStateSQL);
                                    psUpdateState.setInt(1, cakeId);
                                    psUpdateState.executeUpdate();

                                    // 결제 처리
                                    System.out.print("결제 수단을 선택하세요 (card / transfer): ");
                                    String method = sc.nextLine();
                                    int payout = totalPrice;
                                    String paymentSQL = "INSERT INTO payment (order_id, tot_price, payout, payment_method, payment_date) VALUES (?, ?, ?, ?, NOW())";
                                    PreparedStatement psPayment = conn.prepareStatement(paymentSQL);
                                    psPayment.setInt(1, orderId);
                                    psPayment.setInt(2, totalPrice);
                                    psPayment.setInt(3, payout);
                                    psPayment.setString(4, method);
                                    psPayment.executeUpdate();

                                    System.out.println("✅ 주문과 결제가 완료되었습니다!");
                                }

                                else if (menu == 2) {
                                    while (true) {
                                        System.out.println("\n👤 [메인 메뉴] - [내 주문 관리]");
                                        System.out.println("1. 주문 상세 조회");
                                        System.out.println("2. 주문 내역 전체 보기");
                                        System.out.println("3. 주문 수정/취소");
                                        System.out.println("0. 뒤로가기");
                                        System.out.print("선택 >> ");
                                        int orderMenu = sc.nextInt();
                                        sc.nextLine();

                                        if (orderMenu == 1) {
                                            System.out.println("\n🧾 [메인 메뉴] - [내 주문 관리] - [상세 주문 내역 보기]");
                                            System.out.print("조회할 주문 번호 입력하세요: ");
                                            int orderId = sc.nextInt();
                                            sc.nextLine();

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
                                            System.out.print("선택 >> ");
                                            int subMenu = sc.nextInt();
                                            sc.nextLine();

                                            if (subMenu == 1) {
                                                System.out.print("수정할 주문 번호를 입력하세요: ");
                                                int orderId = sc.nextInt();
                                                sc.nextLine();

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
                                                    System.out.println("❌ 본인의 주문만 수정할 수 있습니다.");
                                                    continue;
                                                }

                                                System.out.print("새 수령일 (YYYY-MM-DD): ");
                                                String newPickupDate = sc.nextLine();

                                                System.out.print("새 요청사항: ");
                                                String newRequest = sc.nextLine();

                                                String updateOrdersSQL = "UPDATE orders SET pickup_date = ?, request = ? WHERE order_id = ?";
                                                PreparedStatement psUpdateOrders = conn.prepareStatement(updateOrdersSQL);
                                                psUpdateOrders.setString(1, newPickupDate);
                                                psUpdateOrders.setString(2, newRequest);
                                                psUpdateOrders.setInt(3, orderId);
                                                int rowsUpdated = psUpdateOrders.executeUpdate();

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

                                                // 1. 주문한 케이크 ID와 수량 조회
                                                // 1. 주문한 케이크 ID와 수량 조회
                                                String selectItemsSQL = "SELECT cake_id, quantity FROM orderitem WHERE order_id = ?";
                                                PreparedStatement psSelectItems = conn.prepareStatement(selectItemsSQL);
                                                psSelectItems.setInt(1, orderId);
                                                ResultSet rsItems = psSelectItems.executeQuery();

                                                while (rsItems.next()) {
                                                    int cakeId = rsItems.getInt("cake_id");
                                                    int qty = rsItems.getInt("quantity");

                                                    // 2. 재고 복구
                                                    String updateStockSQL = "UPDATE cake SET stock_quantity = stock_quantity + ? WHERE cake_id = ?";
                                                    PreparedStatement psRestoreStock = conn.prepareStatement(updateStockSQL);
                                                    psRestoreStock.setInt(1, qty);
                                                    psRestoreStock.setInt(2, cakeId);
                                                    psRestoreStock.executeUpdate();

                                                    // 3. 상태 '판매중'으로 업데이트 (재고 0 이상이면)
                                                    String updateStateSQL = "UPDATE cake SET state = '판매중' WHERE cake_id = ? AND stock_quantity > 0";
                                                    PreparedStatement psUpdateState = conn.prepareStatement(updateStateSQL);
                                                    psUpdateState.setInt(1, cakeId);
                                                    psUpdateState.executeUpdate();
                                                }


                                                // 3. orderitem → payment → orders 순서로 삭제
                                                String deleteOrderItemSQL = "DELETE FROM orderitem WHERE order_id = ?";
                                                PreparedStatement psDeleteOrderItem = conn.prepareStatement(deleteOrderItemSQL);
                                                psDeleteOrderItem.setInt(1, orderId);
                                                psDeleteOrderItem.executeUpdate();

                                                String deletePaymentSQL = "DELETE FROM payment WHERE order_id = ?";
                                                PreparedStatement psDeletePayment = conn.prepareStatement(deletePaymentSQL);
                                                psDeletePayment.setInt(1, orderId);
                                                psDeletePayment.executeUpdate();

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
                                    String sql = "SELECT c.cake_name FROM orders o " +
                                            "JOIN orderitem i ON o.order_id = i.order_id " +
                                            "JOIN cake c ON i.cake_id = c.cake_id " +
                                            "WHERE DATE_FORMAT(o.order_date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') " +
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
                    System.out.println("\n📝 [회원가입]");
                    System.out.print("아이디를 입력하세요: ");
                    String newLoginId = sc.nextLine();
                    System.out.print("비밀번호를 입력하세요: ");
                    String newPw = sc.nextLine();
                    System.out.print("이름을 입력하세요: ");
                    String name = sc.nextLine();
                    System.out.print("전화번호를 입력하세요: ");
                    String phone = sc.nextLine();

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
