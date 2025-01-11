package kadai_007;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		PreparedStatement preparedStatement=null;
	
		try {
			con=DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Ai08ng-23gt"
					);
			System.out.println("データベース接続成功");
			
			statement =con.createStatement();
			String sql ="""
					CREATE TABLE IF NOT EXISTS posts (
					post_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
					user_id INT(11) NOT NULL,
					posted_at DATE NOT NULL, 
					post_content VARCHAR(255) NOT NULL,
					likes INT(11) DEFAULT 0
					);
					
				     """;
			int rowCnt = statement.executeUpdate(sql);
			System.out.println("テーブルを作成:rowCnt=" + rowCnt );
			String[][] postList= {
					{"1003","2023-02-08","昨日の夜は徹夜でした・・","13"},
					{"1002","2023-02-08","お疲れ様です！","12"},
					{"1003","2023-02-08","今日も頑張ります！","18"},
					{"1001","2023-02-09","無理は禁物ですよ！","17"},
					{"1002","2023-02-10","明日から連休ですね！","20"}
					
			};
			String insertSql="INSERT INTO posts (user_id, posted_at,post_content,likes)VALUES(?,?,?,?);";
			int insertRowCnt=0;
			preparedStatement=con.prepareStatement(insertSql);
			for(int i=0;i<postList.length; i++) {
				preparedStatement.setString(1,postList[i][0]);
				preparedStatement.setString(2,postList[i][1]);
				preparedStatement.setString(3,postList[i][2]);
				preparedStatement.setString(4,postList[i][3]);
				insertRowCnt=preparedStatement.executeUpdate();
			}
			System.out.println(insertRowCnt + "件のレコードが追加されました");
			String sqlSelect ="SELECT posted_at,post_content,likes FROM posts WHERE user_id=1002;";
			ResultSet result =statement.executeQuery(sqlSelect);
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				String likes =result.getString("likes");
				
				System.out.println(result.getRow()+"件目:投稿日時="+postedAt+"/投稿内容="+postContent+"/いいね数="+likes);
				
			}
		
			}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			if(statement !=null) {
				try { statement.close(); } catch(SQLException ignore) {}
			}
			if( con !=null) {
				try { con.close(); } catch(SQLException ignore) {}
		
		}
		}
		
	}
}
