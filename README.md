jleaf
==================================
	
极速,简单,解耦的MVC框架
---------------------------------
	
### Controller
	
> 采用注解式@Controller方式标示Controller,Controller是一个普通的java类,无需继承什么,启动服务器时能自动扫描到
	
> @Controller有两个参数,都不写时默认按类名首字母小写,同时去掉后面的"Controller"或"Action"为Controller的uri,同时
    
> 可以任何请求方式访问
    
> 两个参数分别是@Controller(value, method)
    
> value代表Controller的uri, method代表请求的方式,未枚举类:HttpMethod,可以是GET,POST,PUT,DELETE,HEAD,OPTIONS,NONE;
    
> 当method为NONE时,表示什么方式都能访问,默认是采用这样
    
> 同时也可指定Controller里的某方法的访问方式, 采用 @Method(HttpMethod.GET) 标签
	
	@Controller("user")
	public class UserController {
		
		@Method(HttpMethod.GET)
		public Result index(ActionRequest actionReq){
			return JspResult("index.jsp");
		}
		
		@Method(HttpMethod.NONE)
		public Result index(ActionRequest actionReq){
		
			Map map = new HashMap();
			map.put("a", "1");
			map.put("b", 2);
			map.put("c", new Date());
			
			//JspResult会将传递进去的对象存放在Request的Attribute中,名字统一命名为"obj",在页面可以使用EL表达式拿到 ${obj.a}
			return JspResult("index.jsp",map);
		}
	}
	
### 对javaee的request,session解耦,采用Map方式:
    
> ActionRequest.getParams();//获得Http请求前端传递过来的参数
    
> ActionRequest.getSession();//获得session(对此map的操作会同步到对应的session中去)
    
> ActionRequest.toObj(?);//方便将请求参数装为对象
	
### 对请求的解析方便获得:
    
> AnalyzeResult analyzeResult = ActionRequest.getAnalyzeResult();
    
> analyzeResult可得到请求的Controller的uri,以及method和访问后缀,前端请求方式(post,get,put,delete等)
	
> 举个使用访问后缀的实例:
	
> 在请求的地址上加上后缀 .json,让后台访问json数据, .xml则返回xml数据,其他则跳网页
	
> 请求: http://localhost:9090/user/data.json
	
> 后台Java代码:
    
	public Result data(ActionRequest actionReq){
		
		Stri ng postfix = actionReq.getAnalyzeResult().getPostfix();
		
		if(".json".equals(postfix)){
			return JsonResult(new User());
		}if(".xml".equals(postfix)){
			return XmlResult(new User());
		}else{
			return JspResult("index.jsp");
		}
	}
	
### 多种Result,同时方便扩展:
	    
> JspResult 返回jsp页面,
    
> ForwardResult 后台forward,
    
> IOResult 返回文件或者IO流,
    
> ImageResult 基于IOResult,response的返回类型声明为图片,
    
> JsonResult 返回json数据,
    
> NullResult 什么都不操作,
    
> RediretResult 前端跳转,
    
> StringResult 返回字符窜
	
> 上面的****Result皆继承Result类,实现其方法:
    
> public abstract void render(HttpServletRequest req,HttpServletResponse resp) throws Exception,
    
> 便能可很方便的扩展其他返回类型,例如VelocityResult,FreemarkerResult,ExcelResult之类的东西
    
	
### Intercepter
	
> 分为全局Intercepter,类级别Intercepter,方法级别Intercepter
	
> 用户自定义的Interceptor皆需继承Interceptor,实现intercept方法,例如:
		
    public class BaseInitInterceptor implements Interceptor {
	
		public boolean intercept(ActionInvocation ai) {
			
			HttpServletRequest request = WebUtils.getRequest();
			if(request != null){
				String basePath = request.getScheme() + "://" + request.getServerName()
						+ ":" + request.getServerPort() + request.getContextPath()
						+ "/";
				request.setAttribute("base", basePath);
			}
			return ai.invoke();
		}
	
	}
	
> 当intercept方法返回false之后,将不会执行接下去的Interceptor
	
> @GlobalInterceptor标识在Interceptor上,将会被扫描到并作为全局Interceptor,拦截所有Controller的请求.
	
	类级别级别的Interceptor:
	@Controller
	@Interceptors(ClassInterceptor.class)//添加类级别的Interceptor
	public class UserController {
		
		@Interceptors(MethodInterceptor.class)//添加方法级别的Interceptor
		public Result index(ActionRequest ar) {
			log.debug(":index");
			return new JspResult("/index.jsp");
		}
	
	}
	
> 如果想不使用上一层的Intercepter,可采用@ClearInterceptor,负责清空上一层次的Intercepter,比如:
	
	@Controller
	@ClearInterceptor
	@Interceptors(ClassInterceptor.class)//添加类级别的Interceptor
	public class UserController {
    
	}
	
> 这样会清除掉通过@GlobalInterceptor添加的全局Intercepter.
	
> 如果在方法上添加:
    
	@ClearInterceptor
	@Interceptors(MethodInterceptor.class)//添加方法级别的Interceptor
	public Result index(ActionRequest ar) {
		log.debug(":index");
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", 2);
		map.put("c", new Date());
		return new JspResult("/index.jsp", map);
	}
	
> 这样会清除掉@GlobalInterceptor添加的全局Intercepter和类级别添加的Interceptor
	
### 方便Junit测试,无需依赖第三方jar包:
	
	static{
		JleafMVC.getInstance().getControllerManager().add(UserController.class);//添加Controller
		
		JleafMVC.getInstance().getControllerManager().addInterceptor(BaseInitInterceptor.class);//添加全局Interceptor
	}
	
	@Test
	public void test() {
		
		Map params = new HashMap();//作为request的paramemter
		Map session = new HashMap();//作为session
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "login", "", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = mvc.doAction(actionRequest);
	}
	
	
	@Test
	public void test() {
		
		Map params = new HashMap();//作为request的paramemter
		Map session = new HashMap();//作为session
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "login", ".json", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = mvc.doAction(actionRequest);
	}
	
> 在不通过web服务器启动项目来测试时,是不会启用扫描功能的,所以需手动将Controller和全局Interceptor加进控管理器
	
> 如果想启用扫描器,则可以:
	
	static{
		//JleafMVC.getInstance().getControllerManager().add(UserController.class);//添加Controller
		
		//JleafMVC.getInstance().getControllerManager().addInterceptor(BaseInitInterceptor.class);//添加全局Interceptor
		
		JleafMVC.scan(new String[]{"org.demo.controller.*","org.demo.interceptor.*"});//启用扫描器,参数为要扫描的地方,只支持 '*' 和 '?', '?'代表一个字符, '*' 代表所有
	}

> 对于maven的测试单元, test生成的class文件夹目录跟src生成的class目录不一致,且内存中使用的是test的目录地址,
	
> 所以进行JUnit测试时,需要自己手动把Controller和Interceptor添加进管理器中,不然,想启用扫描就得自己手动去配置地址:
	
	JleafMVC.getInstance().scan("F:/Java/project/jleaf/jleaf/src/main/webapp/WEB-INF/classes",new String[]{"org.demo.*"});
	
> 对于jar包的扫描也一样要定位jar的目录去:
	
	JleafMVC.getInstance().scan("F:/Java/project/jleaf/jleaf/src/main/webapp/WEB-INF/lib",new String[]{"org.demo.*"});