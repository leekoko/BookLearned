package org.litespring.test.v1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		//��ȡ�����ļ�     ����ӿڱ��
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		//У�������ļ�����
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		//ʵ��������
		PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
		//�ж϶���ɹ�ʵ������
		assertNotNull(petStore);
	}

}