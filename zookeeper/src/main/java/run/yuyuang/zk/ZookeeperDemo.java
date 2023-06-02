package run.yuyuang.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperDemo implements Watcher, AsyncCallback.StatCallback {

    private static final String ZK_HOST = "192.168.3.14:2181";

    private static final String WATCH_PATH = "/YuYang";

    private static ZooKeeper zooKeeper;

    private static final CountDownLatch latch = new CountDownLatch(1);
    private boolean flag = false;

    private final Runnable runnable = () -> {
        int i = 0;
        flag = true;
        while (true) {
            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperDemo demo = new ZookeeperDemo();
        zooKeeper = new ZooKeeper(ZK_HOST, 60000, demo);
        zooKeeper.exists(WATCH_PATH, true, demo, null);
        latch.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.None) {

        } else {
            System.out.println(watchedEvent.getPath());
            if (!watchedEvent.getPath().isEmpty() && watchedEvent.getPath().equals(WATCH_PATH)) {
                zooKeeper.exists(WATCH_PATH, this, this, null);
            }
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        System.out.println(stat);
        if (stat == null) {
            if (flag) {
                System.out.println("latch.countDown");
                latch.countDown();
            }
        } else {
            System.out.println("start run");
            new Thread(runnable).start();
            System.out.println("end run");
        }
    }
}
