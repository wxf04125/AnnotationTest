
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // �࣬�ӿڻ�enum
@Retention(RetentionPolicy.RUNTIME)
// ���������ע��
public @interface DBTable {
	public String name() default "";
}