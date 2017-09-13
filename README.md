##retrofit2 with spring4

###Demo

    #### 配置文件
    
   	<!--http连接池配置-->
   	<bean id="connectionPool" class="okhttp3.ConnectionPool">
   		<constructor-arg index="0"  value="100"/>
   		<constructor-arg index="1"  value="100"/>
   		<constructor-arg index="2"  value="MINUTES"/>
   	</bean>
    
    <!--httpapi 扫描配置-->
   	<bean class="com.msimw.httpservice.client.spring.HttpServiceScannerConfigurer">
   		<property name="connTimeOut" value="15"></property>
   		<property name="writeTimeOut" value="15"></property>
   		<property name="readTimeOut" value="15"></property>
   		<property name="connectionPool" ref="connectionPool"></property>
   		<property name="basePackage" value="com"></property>
   	</bean>
   	
   	#### 接口
   	
   	/**
     * Created by msimw on 17-9-13.
     */
    public interface IPushHttpService<T> {
    
        @POST("b")
        public Call<String> push();
    }
    
    
    @HttpApi("http://www.baidu.com/")
    public interface IBaiduPushHttpService extends IPushHttpService<String>{
    
    }
    
    #### junit
    
    public class DemoTest {
    
      @Autowired
      private IPushHttpService<String> pushHttpService;
    
    
      @Test
      public void oneTest() throws IOException {
          this.pushHttpService.push().execute();
      }
    
    
    }