package com.study.redis.order;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import com.study.redis.lock.RedisLock;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 
 * @Title: JedisProTest
 * @Description:
 * @see http://blog.csdn.net/u012572955/article/details/51223559
 * @see http://blog.csdn.net/liubenlong007/article/details/53766734
 * @Author: zhaotf
 * @Since:2017年9月18日 上午11:37:24
 * @Version:1.0
 */
public class JedisProTest {
	private static Logger logger = Logger.getLogger(JedisProTest.class);
	private JedisCluster jedisCluster; // redis集群
	static String prefix = "luffi:lbl";
	static String KEY_SPLIT = ":"; // 用于隔开缓存前缀与缓存键值
	private String nameKey = prefix + KEY_SPLIT + "name";

	public static void main(String[] args) {
		JedisProTest jpt = new JedisProTest();
		jpt.init();
		try {
			// 将 key 中储存的数字值增一
			jpt.incrTest();
			
			RedisTemplate redisTemplate = new RedisTemplate();
//			redisTemplate.set
			
			
		} catch (InterruptedException e) {
			logger.error(e);
		}
		jpt.end();
	}

	public void init() {
		String[] serverArray = "192.168.159.131:6379".split(",");// redis集群地址
		Set<HostAndPort> nodes = new HashSet<>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}

		// 注意：这里超时时间不要太短，他会有超时重试机制。而且其他像httpclient、dubbo等RPC框架也要注意这点
		jedisCluster = new JedisCluster(nodes, 1000, 1000, 1, "123456", new GenericObjectPoolConfig());

		// 大多数测试都是使用【nameKey】测试的，所以在启动之前先把这个key删掉
		jedisCluster.del(nameKey);
	}

	public void end() {
		try {
			if (jedisCluster != null) {
				jedisCluster.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * setnx : 如果key存在，返回0，如果不存在，则设置成功。 setnx的意思是set if not exist.
	 */
	public void setnxTest() {
		System.out.println(jedisCluster.setnx(nameKey, "张三"));// key不存在，返回值为1
		System.out.println(jedisCluster.get(nameKey));

		System.out.println(jedisCluster.setnx(nameKey, "张三"));// 已经存在，返回值为0
		System.out.println(jedisCluster.get(nameKey));
	}

	/**
	 * incrf: 将 key 中储存的数字值增一。
	 * 
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * 
	 * 这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数来执行 INCR
	 * 操作。
	 * 
	 * 返回值： 执行 INCR 命令之后 key 的值。
	 * 
	 * 这里有问题，最终数据结果大于10000 后续在研究 TODO 这是因为设置的超时时间太小了，他去重试了，所以最终结果大于10000
	 */
	public void incrTest() throws InterruptedException {
		/**
		 * 测试线程安全
		 */
		jedisCluster.del("incrNum");
		final AtomicInteger atomicInteger = new AtomicInteger(0);
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					// 每个线程增加1000次，每次加1
					for (int j = 0; j < 1000; j++) {
						atomicInteger.incrementAndGet();
						logger.info(jedisCluster.incr("incrNum"));
					}

					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		System.out.println(jedisCluster.get("incrNum"));
		System.out.println(atomicInteger);
	}

	/**
	 * Set集合
	 */

	public void setTest() throws InterruptedException {
		String keyA = "{" + prefix + KEY_SPLIT + "set}a";
		String keyB = "{" + prefix + KEY_SPLIT + "set}b";
		jedisCluster.del(keyA);
		jedisCluster.del(keyB);

		System.out.println(jedisCluster.sadd(keyA, "a", "b", "c"));// 给集合添加数据
		System.out.println(jedisCluster.sadd(keyA, "a"));// 给集合添加数据.集合是不可以重复的
		System.out.println(jedisCluster.sadd(keyA, "d"));// 给集合添加数据
		System.out.println(jedisCluster.smembers(keyA));// 返回集合所有数据
		System.out.println(jedisCluster.scard(keyA));// 返回集合长度
		System.out.println("c是否在集合A中：" + jedisCluster.sismember(keyA, "c"));// 判断
																			// member
																			// 元素是否集合
																			// key
																			// 的成员。
		/*
		 * 从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数： 如果 count
		 * 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count
		 * 大于等于集合基数，那么返回整个集合。 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为
		 * count 的绝对值。
		 */
		System.out.println(jedisCluster.srandmember(keyA));// 返回集合中的一个随机元素。
		System.out.println(jedisCluster.spop(keyA)); // 移除并返回集合中的一个随机元素。
		System.out.println(jedisCluster.smembers(keyA));// 返回集合所有数据
		System.out.println("---------");

		/*
		 * SMOVE 是原子性操作。 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回
		 * 0 。否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。 当 destination
		 * 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。 当 source 或
		 * destination 不是集合类型时，返回一个错误。
		 * 注：不可以在redis-cluster中使用SMOVE：redis.clients.jedis.exceptions.
		 * JedisClusterException: No way to dispatch this command to Redis
		 * Cluster because keys have different slots.
		 * 解决办法可以参考上面的mset命令，使用“{}”来讲可以设置的同一个slot中
		 */
		System.out.println(jedisCluster.smove(keyA, keyB, "a"));// 返回集合所有数据
		System.out.println("keyA: " + jedisCluster.smembers(keyA));// 返回集合所有数据
		System.out.println("keyB: " + jedisCluster.smembers(keyB));// 返回集合所有数据

		System.out.println(jedisCluster.sadd(keyB, "a", "f", "c"));// 给集合添加数据
		System.out.println(jedisCluster.sdiff(keyA, keyB));// 差集 keyA-keyB
		System.out.println(jedisCluster.sinter(keyA, keyB));// 交集
		System.out.println(jedisCluster.sunion(keyA, keyB));// 并集
	}

	/**
	 * sortedSet集合
	 */

	public void sortedSetTest() throws InterruptedException {
		String keyA = "{" + prefix + KEY_SPLIT + "sortedSet}a";
		String keyB = "{" + prefix + KEY_SPLIT + "sortedSet}b";
		String keyC = "{" + prefix + KEY_SPLIT + "sortedSet}c";

		jedisCluster.del(keyA);
		jedisCluster.del(keyB);

		System.out.println(jedisCluster.zadd(keyA, 10, "aa"));
		Map<String, Double> map = new HashMap<>();
		map.put("b", 8.0);
		map.put("c", 4.0);
		map.put("d", 6.0);
		System.out.println(jedisCluster.zadd(keyA, map));
		System.out.println(jedisCluster.zcard(keyA));// 返回有序集 key 的数量。
		System.out.println(jedisCluster.zcount(keyA, 3, 8));// 返回有序集 key
															// 中score某个范围的数量。
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));// 返回有序集
																			// key
																			// 中，指定区间内的成员。按score从小到大
		System.out.println("zrevrange: " + jedisCluster.zrevrange(keyA, 0, -1));// 返回有序集
																				// key
																				// 中，指定区间内的成员。按score从大到小
		System.out.println("zrangeWithScores: " + jedisCluster.zrangeWithScores(keyA, 0, -1));// 返回有序集
																								// key
																								// 中，指定区间内的成员。按score从小到大.包含分值

		System.out.println("zscore: " + jedisCluster.zscore(keyA, "aa"));

		/*
		 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按
		 * score 值递增(从小到大)次序排列。 具有相同 score 值的成员按字典序(lexicographical
		 * order)来排列(该属性是有序集提供的，不需要额外的计算)。
		 */
		System.out.println("zrangeByScore: " + jedisCluster.zrangeByScore(keyA, 3, 8));
		System.out.println("zrank: " + jedisCluster.zrank(keyA, "c"));// 返回有序集
																		// key
																		// 中成员
																		// member
																		// 的排名。其中有序集成员按
																		// score
																		// 值递增(从小到大)顺序排列。
		System.out.println("zrevrank: " + jedisCluster.zrevrank(keyA, "c"));// 返回有序集
																			// key
																			// 中成员
																			// member
																			// 的排名。其中有序集成员按
																			// score
																			// 值递增(从大到小)顺序排列。

		System.out.println("zrem: " + jedisCluster.zrem(keyA, "c", "a"));// 移除有序集
																			// key
																			// 中的一个或多个成员，不存在的成员将被忽略。
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));

		System.out.println("zremrangeByRank: " + jedisCluster.zremrangeByRank(keyA, 1, 2));// 按下标删除
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));
		System.out.println("zremrangeByScore: " + jedisCluster.zremrangeByScore(keyA, 1, 3));// 按评分删除
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));

		/*
		 * 接下来这几个操作，需要使用"{}"使得key落到同一个slot中才可以
		 */
		System.out.println("-------");
		System.out.println(jedisCluster.zadd(keyB, map));
		System.out.println("zrange: " + jedisCluster.zrange(keyB, 0, -1));
		/*
		 * ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight
		 * ...]] [AGGREGATE SUM|MIN|MAX] 计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以
		 * numkeys 参数指定，并将该并集(结果集)储存到 destination 。 默认情况下，结果集中某个成员的 score
		 * 值是所有给定集下该成员 score 值之 和 。 WEIGHTS 使用 WEIGHTS 选项，你可以为 每个 给定有序集 分别
		 * 指定一个乘法因子(multiplication factor)，每个给定有序集的所有成员的 score
		 * 值在传递给聚合函数(aggregation function)之前都要先乘以该有序集的因子。 如果没有指定 WEIGHTS
		 * 选项，乘法因子默认设置为 1 。 AGGREGATE 使用 AGGREGATE 选项，你可以指定并集的结果集的聚合方式。 默认使用的参数
		 * SUM ，可以将所有集合中某个成员的 score 值之 和 作为结果集中该成员的 score 值；使用参数 MIN
		 * ，可以将所有集合中某个成员的 最小 score 值作为结果集中该成员的 score 值；而参数 MAX 则是将所有集合中某个成员的 最大
		 * score 值作为结果集中该成员的 score 值。
		 */
		System.out.println("zunionstore: " + jedisCluster.zunionstore(keyC, keyA, keyB));// 合并keyA和keyB并保存到keyC中
		System.out.println("zrange: " + jedisCluster.zrange(keyC, 0, -1));
		System.out.println("zinterstore: " + jedisCluster.zinterstore(keyC, keyA, keyB));// 交集
		System.out.println("zrange: " + jedisCluster.zrange(keyC, 0, -1));
	}

	/**
	 * 列表 排序
	 */

	public void sort() throws InterruptedException {
		String key = prefix + KEY_SPLIT + "queue";
		jedisCluster.del(key);

		System.out.println(jedisCluster.lpush(key, "1", "5", "3", "20", "6"));
		System.out.println(jedisCluster.lrange(key, 0, -1));

		System.out.println(jedisCluster.sort(key));
		System.out.println(jedisCluster.lrange(key, 0, -1));
	}

	/**
	 * lua脚本
	 */

	public void script() throws InterruptedException {

		/*
		 * 其中 "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 是被求值的 Lua 脚本，数字 2
		 * 指定了键名参数的数量， key1 和 key2 是键名参数，分别使用 KEYS[1] 和 KEYS[2] 访问，而最后的 first 和
		 * second 则是附加参数，可以通过 ARGV[1] 和 ARGV[2] 访问它们。
		 *
		 * 注意，这里一些操作不适用于redis-cluster，主要还是因为不同的key被分配到了不同的slot中
		 */
		Object eval = jedisCluster.eval("return {KEYS[1],ARGV[1],ARGV[2]}", 1, "lua", "key1", "dd");
		System.out.println(eval);

		// 脚本里使用的所有键都应该由 KEYS 数组来传递：
		// 因为：所有的 Redis 命令，在执行之前都会被分析，籍此来确定命令会对哪些键进行操作。因此，对于 EVAL
		// 命令来说，必须使用正确的形式来传递键，才能确保分析工作正确地执行
		System.out.println(jedisCluster.eval("return redis.call('set', KEYS[1], ARGV[1])", 1, "luaTest", "cv"));
		System.out.println(jedisCluster.get("luaTest"));

		// 注意这里需要指定KEY，因为这里lua脚本也是和slot挂钩的
		String scriptLoad = jedisCluster.scriptLoad("return redis.call('get', KEYS[1])", "luaTest");// 加载脚本
		System.out.println(scriptLoad);// 返回的SHA1校验和，后续可以直接使用这个进行操作。
		System.out.println(jedisCluster.scriptExists(scriptLoad, "luaTest"));// 检查是否存在

		System.out.println(jedisCluster.evalsha(scriptLoad, 1, "luaTest"));// 执行lua脚本

		System.out.println(jedisCluster.scriptFlush("luaTest".getBytes()));// 删除KEY
																			// as
																			// 上的所有lua脚本
		System.out.println(jedisCluster.scriptExists(scriptLoad, "luaTest"));
		System.out.println(jedisCluster.evalsha(scriptLoad, 1, "luaTest"));// 脚本已经删除，返回错误：NOSCRIPT
																			// No
																			// matching
																			// script.
																			// Please
																			// use
																			// EVAL.
	}

	/**
	 * redis中的lua脚本做了很多限制，防止随机性的发生。比如lua脚本中返回的总是有序的集合。 详情见
	 * http://doc.redisfans.com/script/eval.html - 纯函数脚本
	 */
	public void scriptFuc() throws InterruptedException {
		String key = "luaTest";
		System.out.println(jedisCluster.del(key));
		System.out.println(jedisCluster.sadd(key, "10", "3", "7", "40", "6"));
		System.out.println(jedisCluster.smembers(key));// 这里怎么返回的值是有序的？ [3, 6,
														// 7, 10, 40]
		System.out.println(jedisCluster.eval("return redis.call('smembers', KEYS[1])", 1, key));// 根据字母序排序
																								// [10,
																								// 3,
																								// 40,
																								// 6,
																								// 7]
	}

	/**
	 * 使用lua脚本记录日志
	 * 
	 * @throws InterruptedException
	 */
	public void redisLog() throws InterruptedException {
		// 这里后面必须要有key？？？
		System.out.println(
				jedisCluster.eval("redis.log(redis.LOG_WARNING, 'Something is wrong with this script.')", 1, "afd"));

	}

	/**
	 * 模拟先进先出 定长 队列 参考
	 * http://www.cnblogs.com/stephen-liu74/archive/2012/02/14/2351859.html
	 * https://www.v2ex.com/t/65663
	 */
	public void queue1() throws InterruptedException {

		String key = prefix + KEY_SPLIT + "queue";
		Long del = jedisCluster.del(key);
		System.out.println(del);
		// 我们定义队列长度是5
		int length = 5;

		System.out.println(jedisCluster.lpush(key, "1", "2", "3", "4", "5", "6", "7"));

		List<String> list = jedisCluster.lrange(key, 0, -1);// 打印所有数据
		System.out.println(list);

		Long llen = jedisCluster.llen(key);
		System.out.println("目前队列长度：" + llen);

		/**
		 * 该命令将仅保留指定范围内的元素 每次lpush以后，就用ltrim进行截取，已达到定长队列（或定长list）的目的
		 *
		 * 注意： 这里根据实际业务需求，超长了不一定截取丢掉，也可以进行分流限流报警处理或者其他阻塞操作
		 */
		System.out.println(jedisCluster.ltrim(key, 0, length - 1));
		System.out.println(jedisCluster.lrange(key, 0, -1));
	}

	/**
	 * 分布式互斥锁 一般是通过 set nx ex 实现的 但是这样不完美，具体参考 http://ifeve.com/redis-lock/
	 */
	public void lock() throws InterruptedException {
		String key = prefix + KEY_SPLIT + "lock";
		/**
		 * 复合命令： SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX]
		 */
		System.out.println(jedisCluster.set(key, "本机ID", "nx", "ex", 3));

		for (int i = 0; i < 6; i++) {
			System.out.println(jedisCluster.get(key));
			Thread.sleep(1000);
		}
	}

}
