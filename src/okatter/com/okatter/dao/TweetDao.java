package okatter.com.okatter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import okatter.com.okatter.dto.TweetDto;

public class TweetDao {

	protected Connection connection;

	public TweetDao(Connection connection) {
		this.connection = connection;
	}

	public List<TweetDto> selectAll() throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" TWEET.ID");
		sb.append(" ,TITLE");
		sb.append(" ,BODY");
		sb.append(" ,USER_ID");
		sb.append(" ,USERS.NAME AS POST_NAME");
		sb.append(" ,UPDATE_AT");
		sb.append(" FROM TWEET");
		sb.append(" INNER JOIN USERS");
		sb.append(" ON USER_ID = USERS.ID");
		sb.append(" ORDER BY TWEET.UPDATE_AT DESC");

		List<TweetDto> allTweet = new ArrayList<>();

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				TweetDto dto = new TweetDto();
				dto.setId(rs.getInt("ID"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setBody(rs.getString("BODY"));
				dto.setUserId(rs.getInt("USER_ID"));
				dto.setPostName(rs.getString("POST_NAME"));
				dto.setUpdateAt(rs.getTimestamp("UPDATE_AT").toLocalDateTime());
				allTweet.add(dto);
			}
			return allTweet;
		}
	}

	public TweetDto selectById(TweetDto dto) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" ID");
		sb.append(" ,TITLE");
		sb.append(" ,BODY");
		sb.append(" FROM TWEET");
		sb.append(" WHERE ID = ?");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setInt(1, dto.getId());
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				TweetDto tweet = new TweetDto();
				tweet.setId(rs.getInt("ID"));
				tweet.setTitle(rs.getString("TITLE"));
				tweet.setBody(rs.getString("BODY"));
				return tweet;
			}
			return null;
		}
	}

	public int insertTweet(TweetDto dto) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TWEET(");
		sb.append(" TITLE");
		sb.append(" ,BODY");
		sb.append(" ,USER_ID");
		sb.append(" ,UPDATE_AT");
		sb.append(" ) VALUES (");
		sb.append(" ?");
		sb.append(" ,?");
		sb.append(" ,?");
		sb.append(" ,?");
		sb.append(")");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getBody());
			ps.setInt(3, dto.getUserId());
			ps.setTimestamp(4, Timestamp.valueOf(dto.getUpdateAt()));

			return ps.executeUpdate();
		}
	}

	public int updateTweet(TweetDto dto) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TWEET SET");
		sb.append(" TITLE = ?");
		sb.append(" ,BODY = ?");
		sb.append(" ,UPDATE_AT = ?");
		sb.append(" WHERE ID = ? ");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getBody());
			ps.setTimestamp(3, Timestamp.valueOf(dto.getUpdateAt()));
			ps.setInt(4, dto.getId());

			return ps.executeUpdate();
		}
	}

	public int deleteTweet(TweetDto dto) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TWEET");
		sb.append(" WHERE ID = ?");

		try(PreparedStatement ps = connection.prepareStatement(sb.toString())){
			ps.setInt(1, dto.getId());

			return ps.executeUpdate();
		}
	}
}
