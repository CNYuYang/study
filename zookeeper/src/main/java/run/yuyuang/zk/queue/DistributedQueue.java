package run.yuyuang.zk.queue;

import org.apache.zookeeper.KeeperException;

import java.util.NoSuchElementException;

public interface DistributedQueue {

    /**
     * 获取队首元素
     *
     * @return 队首元素的数据
     * @throws NoSuchElementException NoSuchElementException
     * @throws KeeperException        KeeperException
     * @throws InterruptedException   InterruptedException
     */
    byte[] element() throws NoSuchElementException, KeeperException, InterruptedException;

    byte[] remove() throws NoSuchElementException, KeeperException, InterruptedException;

    byte[] take() throws KeeperException, InterruptedException;

    boolean offer(byte[] data) throws KeeperException, InterruptedException;

    byte[] peek() throws KeeperException, InterruptedException;

    byte[] poll() throws KeeperException, InterruptedException;


}
