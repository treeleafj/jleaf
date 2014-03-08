jleaf
==================================
	
极速,简单,解耦的MVC框架,集成MongoDB和JPA,支持restfull
---------------------------------
### 第一个例子:

	//定义Controller,访问地址为: user/index
	@Control
	public class UserController {
	
		public Result index(HttpAction action){
			return new JspResult("index.jsp");
		}
		
		public Result get(HttpAction action){
			User user = userService.get(action.getParam("id"));
			return new JsonResult(user);
		}
		
	}
	
	//定义全局Interceptor
	@GlobalInterceptor(1) //里面的数字表示拦截顺序
	public class BaseInitInterceptor implements Interceptor {

		//action执行前
	    public boolean begin(ActionInvocation ai) {
	        HttpServletRequest request = ActionContext.getRequest();
	        return true;
	    }
	
		//action执行完成后
	    @Override
	    public void end(ActionInvocation ai) {
	        Result result = ai.getResult();//可以拿到结果
	    }
	
	}
	
	
### 注解:

	@Control(value,method,isSingleton) //指定该普通java类为Controller,method访问方式,isSingleton是否单例
	@Method(HttpMethod.POST) //指定该Controller下面的所有方法得post方式才能访问
	@ClearInterceptor({BaseInitInterceptor.class})   //清除指定的全局Interceptor,如果不指定清除哪个,则默认是清除全部
	@Interceptors({MsgInterceptor.class})	//指定该Controller的私有拦截器
	public class UserController {
	
		@Method(HttpMethod.GET) //指定该方法得get方式才能访问,优先级比Controller上的高
		@ClearInterceptor({MsgInterceptor.class}) //同上
		@Interceptors({MyInterceptor.class})//同上
		public Result index(HttpAction actionReq){
			return JspResult("index.jsp");
		}
		
	}
	
	//定义全局Interceptor
	@GlobalInterceptor(value,clear) //标注为全局Interceptor,value表示拦截顺序,越小越优先,clear表示能否被@ClearInterceptors清除
	public class BaseInitInterceptor implements Interceptor {//继承Interceptor

		//action执行前
	    public boolean begin(ActionInvocation ai) {//ai里包装了很多信息
	        HttpServletRequest request = ActionContext.getRequest();
	        return true;//返回是否继续执行下去,如果false,将会从当前Interceptor开始往前执行end方法
	    }
	
		//action执行完成后
	    @Override
	    public void end(ActionInvocation ai) {
	        Result result = ai.getResult();//可以拿到结果
	    }
	
	}
	
### Controller
	
> 采用注解@Control方式标示Controller,Controller是一个普通的java类,无需继承什么,启动服务器时能自动扫描到
	
> @Control有两个参数,都不写时默认按类名首字母小写,同时去掉后面的"Controller"或"Action","Control"为Controller的uri,同时
    
> 可以指定任何请求方式访问
    
> 两个参数分别是@Control(value, method)
    
> value代表Controller的uri, method代表请求的方式,未枚举类:HttpMethod,可以是GET,POST,PUT,DELETE,HEAD,OPTIONS,NONE;
    
> 当method为NONE时,表示什么方式都能访问,默认是采用这样
    
> 同时也可指定Controller里的某方法的访问方式, 采用 @Method(HttpMethod.GET) 标签
	
### 入参 HttpAction
    
> HttpAction.getParams();//获得Http请求前端传递过来的参数
    
> HttpAction.getSession();//获得session(对此map的操作会同步到对应的session中去)
    
> HttpAction.toObj(?);//方便将请求参数装为对象
	 
> HttpAction.getAnalyzeResult();//拿到Action的解析结果,可得到请求的uri以及HttpMethod和访问后缀

> 举个使用访问后缀的实例:
	
> 在请求的地址上加上后缀 .json,让后台访问json数据, .xml则返回xml数据,其他则跳网页
	
> 请求: http://localhost:9090/user/data.json
	
> 后台Java代码:
    
	public Result data(HttpAction actionReq){
		
		Stri ng postfix = actionReq.getAnalyzeResult().getPostfix();
		
		if(".json".equals(postfix)){
			return JsonResult(new User());
		}if(".xml".equals(postfix)){
			return XmlResult(new User());
		}else{
			return JspResult("index.jsp");
		}
	}
	
> 对于Controller里方法的返回类型为Result接口类型:
	    
> JspResult 返回jsp页面,
    
> ForwardResult 后台forward,可以跳转到jsp或者Controller
    
> IOResult 返回文件或者IO流,
    
> ImageResult 基于IOResult,response的返回类型声明为图片,
    
> JsonResult 返回json数据,
    
> NullResult 什么都不操作,
    
> RediretResult 前端跳转,
    
> StringResult 返回字符窜
	
> 上面的****Result皆继承Result类,实现其方法:
    
> public abstract void render(HttpServletRequest req,HttpServletResponse resp) throws Exception,
    
> 便能可很方便的扩展其他返回类型,例如VelocityResult,FreemarkerResult,ExcelResult之类的东西
    
	
### Intercepter 拦截器
	
> 拦截器分为全局,类级别,方法级别三种,同时皆需实现Interceptor接口
	
	@GlobalInterceptor//标上该注解后,将被扫描到,同时作为全局拦截器,拦截所有的用户请求
    public class BaseInitInterceptor implements Interceptor {
		//action执行前
	    public boolean begin(ActionInvocation ai) {//ai里包装了很多信息
	        HttpServletRequest request = ActionContext.getRequest();
	        return true;
	    }
	
		//action执行完成后
	    @Override
	    public void end(ActionInvocation ai) {
	        Result result = ai.getResult();//可以拿到结果
	    }
	}
	

	//类级别级别的Interceptor:
	@Control
	@Interceptors(ClassInterceptor.class)//添加类级别的拦截器,该Controller下所有方法执行前都会触发类级别的拦截器
	public class UserController {
		
		@Interceptors(MethodInterceptor.class)//添加方法级别的拦截器,同类级别的过滤器是同样道理
		public Result index(HttpAction action) {
			return new JspResult("/index.jsp");
		}
	}
	
> 如果想不使用上一层的拦截器,可采用@ClearInterceptor,负责清空上一层次的Intercepter,比如:
	
	@Control
	@ClearInterceptor
	@Interceptors(ClassInterceptor.class)
	public class UserController {
    
	}
	
> 但如果GlobalInterceptor的clear为NOTCLEAR的则不会清除
	
	
### Junit测试,无需依赖第三方jar包:
	
	//只需继承JleafJunit
	public class UserControllerTest extends JleafJunit {
	
	    @BeforeClass
	    public static void beforeClass() {
	        JleafJunit.setPath("F:/Java/project/jleaf/jleaf/src/demo/webapp");//设置的web目录
	        
	        //设置启动配置(可不设置,但有时想关闭扫描功能或者改变扫描的包路径,可以通过这个设置)
	        Map<String, String> config = new HashMap<String, String>();
	        config.put("package", "org.demo.*");
	        setBootConfig(config);
	    }
	
	    @Test
	    public void testJson() throws Exception {
	        Result result = this.action("user/json", HttpMethod.GET);
	        Assert.assertNotNull(result);
	    }
	
	    @Test
	    public void testIndex() throws Exception {
	        Result result = this.action("user/index", HttpMethod.GET);
	        Assert.assertNotNull(result);
	    }
	
	}

### 对restfull的支持
> 在src目录下建立jleaf.properties,在里面添加:
> jleaf.defaultActionAnalyzeClass=org.jleaf.web.action.analyze.RestfulHttpActionAnalyze
> 这样就把解析用户请求的解析实现类改Restful方式的,然后在Controller中的方法命名:
	
	public RestController{
	
	    public Result get(HttpAction action){
	        return new StringResult("=>get:" + action.getParam("id"));
	    }
	
	    public Result save(HttpAction action){
	        return new StringResult("=>save");
	    }
	
	    public Result update(HttpAction action){
	        return new StringResult("=>update");
	    }
	
	    public Result delete(HttpAction action){
	        return new StringResult("=>delete");
	    }
	}
	
### 自动CRUD的实现
> 参考demo里的MsgController实现

	//继承CrudController自动实现增删改查
	@Control
	public class MsgController extends CrudController<Msg, MsgServiceImpl, MsgQuery> {
	
	}
	
## 数据源的切换(JPA和MongoDB):
> 一样在jleaf.properties里加上:
> jleaf.defaultDaoImpl=org.jleaf.db.dao.impl.MongoDBDaoImpl 或者是
> jleaf.defaultDaoImpl=org.jleaf.db.dao.impl.JPADaoImpl
	
## 结语
> 很多功能都在开发中,比如DB这块也只是粗略实现,spring等插件的继承,页面显示层打算采用的的Extjs和Touch还没扩展成自已的一套框架,这些都会在日后慢慢加上去