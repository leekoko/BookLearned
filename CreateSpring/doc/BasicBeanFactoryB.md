# Basic BeanFactory（下）   

D：看一下这个类，``loadBeanDefinition()``方法有什么问题呢？  

```java
public class DefaultBeanFactory implements BeanFactory {
	
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();
	
	/**
	 * 构造文件
	 * @param configFile
	 */
	public DefaultBeanFactory(String configFile) {
		loadBeanDefinition(configFile);
	}
	/**
	 * 加载xml
	 * @param configFile
	 */
	private void loadBeanDefinition(String configFile) {
		InputStream is = null;
		try {
			ClassLoader cl = ClassUtils.getDefaultClassLoader();
			is = cl.getResourceAsStream(configFile); //读取配置文件
			SAXReader reader = new SAXReader();  //dom4j解析xml文件
			Document doc = reader.read(is);   //读取成Document文件
			
			Element root = doc.getRootElement();  //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				this.beanDefinitionMap.put(id,bd);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BeanDefinitionStoreException("IOException parsing XML document", e);
		}finally {
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public BeanDefinition getBeanDefinition(String beanID) {
		return this.beanDefinitionMap.get(beanID);
	}
	/**
	 * 获取bean对象
	 */
	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);  //获取BeanDefinition对象
		if(bd == null){
			throw new BeanCreationException("Error creating does not exist");
			//return null;
		}
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();   //创建对象
		}
		catch (Exception e) {
			throw new BeanCreationException("Error creating does not exist");
		}
		/*catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/
		
	}

}
```

M：会有什么不妥的地方吗？

Z：``loadBeanDefinition()``做的是xml文件的读取，而``getBean()``做的是对象的获取。违背了SRP单一职责原则。

M：什么是单一职责原则？怎么理解？

Z：比如，一根尺子，既可以用来打学生手板，也可以用来丈量布匹。而在单一职责原理下，尺子的两个功能就是引起这个类变化的两个原因，就应该写成两个类。   





8min  loading



