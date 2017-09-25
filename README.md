## retrofit2 with spring
   Http 接口调用API(对httpclient的封装基于OkHttp3)，支持接口泛型注入。调用http接口就像调用本地接口一样简单（不用编写实现类）
### 文档地址 https://msimw.gitbooks.io/retrofit2-spring/content/

### Maven
    <dependency>
        <groupId>com.github.msimw</groupId>
        <artifactId>retrofit2-spring</artifactId>
        <version>1.2-SNAPSHOT</version>
    </dependency>


### 版本更新说明
    v1.2 1.修改动态获取URL的方式为${}
         2.修复动态获取URL与配置URL冲突BUG

### 功能描述
    1.与spring整合，将httpApi 交由spring容器管理，支持IOC
    2.支持接口继承+泛型注入
    3.支持直接返回结果对象

### 简单使用

#### 1.配置文件
    
        <!--http连接池配置-->
        <bean id="connectionPool" class="okhttp3.ConnectionPool">
            <constructor-arg index="0"  value="100"/>
            <constructor-arg index="1"  value="100"/>
            <constructor-arg index="2"  value="MINUTES"/>
        </bean>
        
        <!--httpapi 扫描配置-->
        <bean class="com.msimw.retrofit2x.spring.HttpApiScannerConfigurer">
            <property name="connTimeOut" value="15"></property>
            <property name="writeTimeOut" value="15"></property>
            <property name="readTimeOut" value="15"></property>
            <property name="connectionPool" ref="connectionPool"></property>
            <property name="basePackage" value="com"></property>
        </bean>

#### 2.接口
        
        
        public interface IPushHttpApi<T> {
      
            @POST("b")
            public String push();
        
        }
        
        
        @HttpApi("http://www.baidu.com/")
        public interface IBaiduPushHttpApi extends IPushHttpApi<String>{
        
        }
    
#### 3.Junit
    
        public class DemoTest {
        
          @Autowired
          private IPushHttpApi<String> pushHttpApi;
        
        
          @Test
          public void oneTest() throws IOException {
              this.pushHttpApi.push();
          }
        
        
        }