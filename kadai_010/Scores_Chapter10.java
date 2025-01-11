
package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		
		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Ai08ng-23gt"
					);
			
			System.out.println("データベース接続成功: " + con);
			
			// SQLクエリを準備
			statement = con.createStatement();
			String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5";
			
			// SQLクエリを実行（DBMSに送信）
			int rowCnt = statement.executeUpdate(updateSql);
			System.out.println(rowCnt + "件のレコードが更新されました");
			
			// データ取得のSQLクエリを準備（数学と英語の点数が高い順に並べる）
			String selectSql = "SELECT id, name, score_math, score_english FROM scores ORDER BY score_math DESC, score_english DESC";
			System.out.println("数学・英語の点数が高い順に並べます");
			
			ResultSet result = statement.executeQuery(selectSql);
			 
			// SQLクエリの実行結果を抽出および出力
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int math = result.getInt("score_math");
				int english = result.getInt("score_english");
				System.out.println(result.getRow() + "件目：生徒ID=" + id
					+ "／氏名=" + name + "／数学=" + math + "／英語=" + english);
			}
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// リソースのクリーンアップ
			if(statement != null) {
				try { statement.close(); } catch (SQLException ignore) {}
			}
			if(con != null) {
				try { con.close(); } catch(SQLException ignore) {}
			}
		}
	}
}
