package org.litespring.test.v1;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.bean.factory.xml.XmlBeanDefinitionReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
	
	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
	
	@Before
	public void setUP(){
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
	}

	@Test
	public void testGetBean() {
		//��ȡ�����ļ�     ����ӿڱ��
		Resource resource = new ClassPathResource("petstore-v1.xml");
		reader.loadBeanDefinition(resource);
//		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		//У�������ļ�����
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		//ʵ��������
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		//�ж϶���ɹ�ʵ������
		assertNotNull(petStore);
	}

	@Test
	public void testInvalidBean(){
		//��ȡ�����ļ�     ����ӿڱ��
		Resource resource = new ClassPathResource("petstore-v1.xml");
		reader.loadBeanDefinition(resource);
//		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		try {
			factory.getBean("invalidBean");
		} catch (Exception e) {
			return;   //�����쳣�򷵻�
		}
		Assert.fail("expect BeanCreationException");
	}
	
	@Test
	public void testInvalidXML(){
		try {
			Resource resource = new ClassPathResource("v1.xml");
			reader.loadBeanDefinition(resource);
//			new DefaultBeanFactory("xxx.xml");
		} catch (BeanDefinitionStoreException e) {
			return;
		}
		Assert.fail("expect BeanCreationException");
	}
	
}
	