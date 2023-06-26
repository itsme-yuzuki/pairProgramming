//import java.util.regex.Pattern;
//
////Javaでパスワードを検証するJavaプログラム
//public class PasswordCheck
//{
//    //両方の場合の数字とアルファベットを必要とする4〜8文字のパスワード
//    private static final String PASSWORD_REGEX =
//            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
// 
//    // 4つのうち少なくとも3つを必要とする4〜32文字のパスワード(大文字
//    //および小文字、数字、特殊文字)および最大で
//    //2つの等しい連続文字。
//    private static final String COMPLEX_PASSWORD_REGEX =
//            "^(?:(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|" +
//            "(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|" +
//            "(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|" +
//            "(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\\1{2,})" +
//            "[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]" +
//            "{8,32}$";
// 
//    private static final Pattern PASSWORD_PATTERN =
//                                    Pattern.compile(COMPLEX_PASSWORD_REGEX);
// 
//    public static void main(String[] args)
//    {
//        String password = "Stream@Java8";
// 
//        //パスワードを検証します
//        if (PASSWORD_PATTERN.matcher(password).matches()) {
//            System.out.print("The Password " + password + " is valid");
//        }
//        else {
//            System.out.print("The Password " + password + " isn't valid");
//        }
//    }
//}