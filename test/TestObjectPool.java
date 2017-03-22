package com.test.objectpool;

public class TestObjectPool {

    public static void main(String[] args) {
        TestObject[] array = new TestObject[16];
        for (int i = 0; i < 16; i++) {
            array[i] = new TestObject();
        }

        final ObjectPool<TestObject> objectPool = new ObjectPool<>(array);

        for (int i = 0; i < 5; i++) {
            new Thread("Thread:" + i) {
                @Override
                public void run() {
                    super.run();
                    for (int j = 0; j < 10; j++) {
                        TestObject testObject = objectPool.obtain();
                        testObject.print(getName(), "---index:" + j );
                        objectPool.recycle(testObject);
                    }
                }
            }.start();
        }

    }

    static class TestObject extends ObjectPool.RecyclableObject {
        public TestObject() {
        }

        public void print(String thread, String index) {
            System.out.println(thread + index);
        }
    }

}
