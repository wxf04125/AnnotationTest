import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
	public Connection getConnection() {
		String user = "root";
		String password = "";
		String serverUrl = "jdbc:mysql://localhost:3306/carrent?user=root&password=";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(serverUrl, user, password);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ʵ�ִ�����
	public static void main(String[] args) {
		TableCreator tc = new TableCreator();
		tc.executeCreateDB(Member.class);
	}

	public void executeCreateDB(Class<?> entity) {
		String sqlStr = explainAnnotation(entity);
		Connection con = getConnection();
		PreparedStatement psql = null;
		if (con != null && !sqlStr.equals("error")) {
			try {
				psql = con.prepareStatement(sqlStr);
				psql.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (psql != null)
						psql.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (con != null)
						psql.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else
			System.out.println("failure to...");
	}

	// �����Ĵ�����,Class<?>�������������
	public String explainAnnotation(Class<?> entity) {
		// ��ȡָ�����͵�ע��
		DBTable dbtable = entity.getAnnotation(DBTable.class);
		if (dbtable == null) {
			System.out.println("No DBTable annotation in class" + entity.getName());
			return "error";
		} else {
			String tableName = dbtable.name();// ��ȡע��nameֵ����������
			// ��û������nameֵ��ֱ�������������Ϊ����
			if (tableName.length() < 1)
				tableName = entity.getName().toUpperCase();// ת����д
			// ׼�������ֶ�ע��
			List<String> columnsDefs = new ArrayList<String>();
			// ��ȡ����������ֶ�
			for (Field field : entity.getDeclaredFields()) {
				String columnName = null;
				// ��ȡ���ֶ����е�ע��
				Annotation[] anns = field.getDeclaredAnnotations();
				// Annotation[] anns=field.getAnnotations();
				// ����ע���ʱ��
				if (anns.length >= 1) {
					// �ж�ע�������
					if (anns[0] instanceof SQLInteger) {
						SQLInteger sInt = (SQLInteger) anns[0];
						// ��û��nameʱ�򣬽��ֶδ�дΪ����
						if (sInt.name().length() < 1)
							columnName = field.getName().toUpperCase();
						else
							columnName = sInt.name();
						columnsDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
					}
					if (anns[0] instanceof SQLString) {
						SQLString sString = (SQLString) anns[0];
						// ��û��nameʱ�򣬽��ֶδ�дΪ����
						if (sString.name().length() < 1)
							columnName = field.getName().toUpperCase();
						else
							columnName = sString.name();
						columnsDefs.add(columnName + " VARCHAR(" + sString.value() + ")"
								+ getConstraints(sString.constraints()));
					}
				}
			}
			StringBuilder createDB = new StringBuilder("CREATE TABLE " + tableName + "(");
			for (String cols : columnsDefs)
				createDB.append(" " + cols + ",");
			// �Ƴ����һ������
			String tableSQL = createDB.substring(0, createDB.length() - 1) + ");";
			// ���������Ĺ���
			System.out.println("Table Creation SQL is:\n" + tableSQL);
			return tableSQL;
		}
	}

	// ����ָ����Լ��
	public String getConstraints(Constraints con) {
		String constras = "";
		if (!con.allowNull())
			constras += " NOT NULL";
		if (con.primaryKey())
			constras += " PRIMARY KEY";
		if (con.unique())
			constras += " UNIQUE";
		return constras;
	}
}