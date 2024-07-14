### WebSecurity 构建流程
1. WebSecurityConfiguration#setFilterChainProxySecurityConfigurer方法
2. new WebSecurity(objectPostProcessor) 实例化 WebSecurity 对象
3. 从Spring容器中获取 WebSecurityConfigurer:WebSecurityConfigurerAdapter 集合
4. 调用WebSecurity#apply -> add(configurer) 将配置保存到 configurers 集合中
### WebSecurityConfiguration#springSecurityFilterChain 构建 springSecurityFilterChain
1. 调用 WebSecurity#build() 构建出 FilterChainProxy
### WebSecurity#build()-> doBuild 流程
1. WebSecurity#init()
2. WebSecurity#configure()
3. var result = WebSecurity#performBuild();
4. return result ;
### WebSecurity#init
1. 遍历configurers 集合并调用init(WebSecurity)方法
2. WebSecurityConfigurerAdapter#init
### WebSecurityConfigurerAdapter#init 流程
1. HttpSecurity http = WebSecurityConfigurerAdapter#getHttp()
2. 调用 WebSecurity#addSecurityFilterChainBuilder(http) 将http 对象保存到 securityFilterChainBuilders 集合中

### WebSecurity#performBuild 流程
1. 取出所有的 securityFilterChainBuilders 遍历，构建出SecurityFilterChain: DefaultSecurityFilterChain
2. new FilterChainProxy(securityFilterChains) 构建FilterChainProxy对象
3. 打印Security 启动日志，并返回 FilterChainProxy 对象

