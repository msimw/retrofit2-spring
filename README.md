## retrofit2 with spring4
   Http接口调用API，使调用http接口就像调用本地接口一样简单。 

### 文档地址 https://msimw.gitbooks.io/retrofit2-spring4/content/

### Demo

    1.配置文件
    
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

    2.接口
    
        public interface IPushHttpApi<T> {
        
            @POST("b")
            public Call<String> push();
        }
        
        
        @HttpApi("http://www.baidu.com/")
        public interface IBaiduPushHttpApi extends IPushHttpApi<String>{
        
        }
    
    3.Junit
    
        public class DemoTest {
        
          @Autowired
          private IPushHttpApi<String> pushHttpApi;
        
        
          @Test
          public void oneTest() throws IOException {
              this.pushHttpApi.push().execute();
          }
        
        
        }