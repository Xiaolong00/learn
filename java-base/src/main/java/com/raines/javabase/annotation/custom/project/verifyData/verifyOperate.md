## 利用java反射和java自定义注解验证数据的完整性  
JDK1.5及以后版本引入的java自定义注解，可以应用到反射中，比如自己写个小框架。如实现实体类某些属性不自动赋值，或者验证某个对象属性完整性等，下面具体说说使用注解对实体数据进行非空校验的过程。  
1. 首先自定义非空注解NotEmpty  
2. 其次定义一个实体类Person，并在部分属性上面加上注解@NotEmpty  
3. 编写测试类Main  

