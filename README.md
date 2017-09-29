## retrofit2 with spring
   Http 接口调用API(对httpclient的封装基于OkHttp3)，支持接口泛型注入。调用http接口就像调用本地接口一样简单（不用编写实现类）
### 文档地址 https://msimw.gitbooks.io/retrofit2-spring/content/

    
### Maven
    <dependency>
        <groupId>com.github.msimw</groupId>
        <artifactId>retrofit2-spring</artifactId>
        <version>1.1</version>
    </dependency>


### 版本更新说明
    v1.4 1.支持用户自定义httpapi资源文件
         2.新增对cookie配置
         3.支持https配置
         4.支持自定义dns配置
         5.支持缓存配置
         

### 功能描述
    1.与spring整合，将httpApi 交由spring容器管理（逆向mvc），支持IOC
    2.支持接口继承+泛型注入
    3.支持直接返回结果对象

### 简单使用

#### 1.配置文件
    
        <!--http连接池配置-->
        <bean id="httpDataSource"  class="com.msimw.retrofit2x.spring.HttpDataSource">
            <property name="maxIdleConnections" value="${httpclient.maxIdleConnection}"></property>
            <property name="keepAliveDurationNs" value="${httpclient.keepAliveDuration}"></property>
            <property name="connTimeOut" value="${httpclient.connTimeOut}"></property>
            <property name="readTimeOut" value="${httpclient.readTimeOut}"></property>
            <property name="writeTimeOut" value="${httpclient.writeTimeOut}"></property>
        </bean>
        
        
        <!--http扫包配置-->
        <bean class="com.msimw.retrofit2x.spring.HttpApiScannerConfigurer">
            <!--与spring扫包配置一样-->
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