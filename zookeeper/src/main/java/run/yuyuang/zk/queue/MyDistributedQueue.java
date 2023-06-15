package run.yuyuang.zk.queue;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

public class MyDistributedQueue implements DistributedQueue {

    private final String dir;

    private List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;

    private final ZooKeeper zookeeper;

    private static final String prefix = "dq-";

    public MyDistributedQueue(ZooKeeper zookeeper, String dir, List<ACL> acl) {
        this.dir = dir;

        if (acl != null) {
            this.acl = acl;
        }
        this.zookeeper = zookeeper;
    }


    private Map<Long, String> orderedChildren(Watcher watcher) throws InterruptedException, KeeperException {
        Map<Long, String> treeMap = new TreeMap<>();

        List<String> nodes = zookeeper.getChildren(dir, watcher);

        for (String node : nodes) {
            if (node.startsWith(prefix)) {
                try {
                    Long id = Long.parseLong(node.substring(3));
                    treeMap.put(id, node);
                } catch (NumberFormatException e) {
                    System.out.println("parseLong error");
                    e.printStackTrace();
                }

            }
        }
        return treeMap;
    }

    @Override
    public byte[] element() throws NoSuchElementException, KeeperException, InterruptedException {
        Map<Long, String> children = orderedChildren(null);
        if (children.isEmpty()) {
            return null;
        }
        while (true) {
            for (Map.Entry<Long, String> entry : children.entrySet()) {
                return zookeeper.getData(dir + "/" + prefix + entry.getValue(), false, null);
            }
        }
    }

    @Override
    public byte[] remove() throws NoSuchElementException, KeeperException, InterruptedException {
        Map<Long, String> children = orderedChildren(null);
        if (children.isEmpty()) {
            return null;
        }
        while (true) {
            for (Map.Entry<Long, String> entry : children.entrySet()) {
                String path = dir + "/" + prefix + entry.getValue();
                byte[] data = zookeeper.getData(path, false, null);
                zookeeper.delete(path, -1);
                return data;
            }
        }
    }

    private static class LatchChildWatcher implements Watcher {

        CountDownLatch latch;

        public LatchChildWatcher() {
            latch = new CountDownLatch(1);
        }

        public void process(WatchedEvent event) {
            latch.countDown();
        }
        public void await() throws InterruptedException {
            latch.await();
        }

    }

    @Override
    public byte[] take() throws KeeperException, InterruptedException {
        LatchChildWatcher watcher = new LatchChildWatcher();
        Map<Long, String> children = orderedChildren(watcher);
        if (children.isEmpty()) {
            watcher.await();
        }
        while (true) {
            for (Map.Entry<Long, String> entry : children.entrySet()) {
                return zookeeper.getData(dir + "/" + prefix + entry.getValue(), false, null);
            }
        }
    }

    @Override
    public boolean offer(byte[] data) throws KeeperException, InterruptedException {
        while (true) {
            try {
                zookeeper.create(dir + "/" + prefix, data, acl, CreateMode.PERSISTENT_SEQUENTIAL);
                return true;
            } catch (KeeperException.NoNodeException e) {
                zookeeper.create(dir, data, acl, CreateMode.PERSISTENT);
            }
        }
    }

    @Override
    public byte[] peek() throws KeeperException, InterruptedException {
        return element();
    }

    @Override
    public byte[] poll() throws KeeperException, InterruptedException {
        return remove();
    }
}
