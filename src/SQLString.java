
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // �࣬�ӿڻ�enum
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
	int value() default 0;

	String name() default "";

	// ע��Ԫ�������ñ��ע�⣬
	Constraints constraints() default @Constraints;
}