
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // �࣬�ӿڻ�enum
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
	String name() default "";

	// Ƕ��ע��Ĺ���,��column���͵����ݿ�Լ����ϢǶ������
	Constraints constraints() default @Constraints;
}