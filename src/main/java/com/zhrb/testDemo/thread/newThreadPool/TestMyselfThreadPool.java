package com.zhrb.testDemo.thread.newThreadPool;

import com.zhrb.testDemo.thread.poiReadExcel.ContentValueEntity;
import com.zhrb.testDemo.thread.poiReadExcel.PoiWrite;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName TestMyselfThreadPool
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/20 16:20
 * @Version
 */
public class TestMyselfThreadPool {
    private final static String fileName = "163672.xls";

    private final static String pathName = "C:\\Users\\Administrator\\Desktop\\";

    private final static File file = new File(pathName + fileName);

    public static void main(String[] args) {
        //factory形式
        CustomThreadFactory threadFactory = new CustomThreadFactory();
        //异常处理逻辑
        CustomRejectedExecutionHandler customRejectedExecutionHandler = new CustomRejectedExecutionHandler();
        //工作队列形式，要定义有界的，否则线程量多的话会OOM
        BlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
        //创建线程池管理
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,8,10,
                TimeUnit.SECONDS, blockingDeque, threadFactory, customRejectedExecutionHandler);
        //使用自定义线程池去读取文件
        operaterExcel2(threadPoolExecutor);
    }

    private static void operaterExcel2(ThreadPoolExecutor es){

        FileInputStream fileInputStream = null;
        Workbook wb = null;
        Sheet sheet = null;
        //创建的线程数
        int threadNum = 4;
        //存放最终结果的map
        final Map<String,List<ContentValueEntity>> map = new HashMap<>(threadNum);
        // 使用线程池进行线程管理
        //final ExecutorService es = Executors.newCachedThreadPool();

        try {
            //把文件读入输入流
            fileInputStream = new FileInputStream(file);
            /**创建工作簿，这一个对象代表着对应的一个Excel文件
             * 用 WorkbookFactory 创建workbook
             * 就 不用判断 2003(xls)（HSSFWorkbook）
             * 与2007(slsx)(XSSFWorkbook)
             * */
            wb = WorkbookFactory.create(fileInputStream);
            //读取sheet
            sheet = wb.getSheetAt(0);
            //第一行数据（第一行是列名，所以开始行+1）
            int first = sheet.getFirstRowNum()+1;
            //最后一行
            int last = sheet.getLastRowNum();
            //全部的行数
            int totalNum = last - first + 1;
            //每个线程处理的行数
            int numOfThread = totalNum/threadNum;
            // int lastThreadNum = totalNum - (threadNum-1)*numOfThread;

            //获取开始时间
            final long startTime=System.currentTimeMillis();

            /**回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
             叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用
             *  假若有若干个线程都要进行写数据操作，
             *  并且只有所有线程都完成写数据操作之后，
             *  这些线程才能继续做后面的事情，
             *  此时就可以利用CyclicBarrier了：
             */
            CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new Runnable() {
                @Override
                public void run() {
                    //当所有线程执行完毕后，便执行此方法
                    List<ContentValueEntity> list1 = map.get("1");
                    List<ContentValueEntity> list2 = map.get("2");
                    List<ContentValueEntity> list3 = map.get("3");
                    List<ContentValueEntity> list4 = map.get("4");
                    List<ContentValueEntity> combineList = new ArrayList<>();
                    combineList.addAll(list1);
                    combineList.addAll(list2);
                    combineList.addAll(list3);
                    combineList.addAll(list4);
                    //获取结束时间
                    long endTime=System.currentTimeMillis();
                    //程序运行时间
                    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

                    System.out.println("ResultList====="+combineList);

                    es.shutdown();
                }
            });
            //这里提交线程去执行，有两个方法，1、submit 2、excuse
            Future<Map<String,List<ContentValueEntity>>> data1 = es.submit(new PoiWrite(cyclicBarrier, sheet, 1, numOfThread,"1",
                    map));
            Future<Map<String,List<ContentValueEntity>>> data2 = es.submit(new PoiWrite(cyclicBarrier, sheet, numOfThread+1, 2*numOfThread,"2",
                    map));
            Future<Map<String,List<ContentValueEntity>>> data3 = es.submit(new PoiWrite(cyclicBarrier, sheet, 2*numOfThread+1, 3*numOfThread,"3",
                    map));
            Future<Map<String,List<ContentValueEntity>>> data4 = es.submit(new PoiWrite(cyclicBarrier, sheet, 3*numOfThread+1, totalNum,"4",
                    map));
        }  catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void countDownLuanchReadExce(ThreadPoolExecutor es) throws IOException {
        FileInputStream fileInputStream = null;
        Workbook wb = null;
        Sheet sheet = null;
        int threadNum = Runtime.getRuntime().availableProcessors();
        //存放结果
        Map<String,List<PhoneNum>> resultMap = new HashMap<>(threadNum);
        //声明多线程模型
        CountDownLatch latch = new CountDownLatch(threadNum);
        //开始读取文件
        try {
            fileInputStream = new FileInputStream(file);
            //把文件流放入到创建的文件模型中
            wb = WorkbookFactory.create(fileInputStream);
            //读取第一个sheet,起始位为0
            sheet = wb.getSheetAt(0);
            //开始读取sheet的行数据
            int firstNum = sheet.getFirstRowNum();
            int lastNum = sheet.getLastRowNum();
            //计算得出所有的行数,第一行为标题头
            int totalNum = lastNum - firstNum + 1;
            // 计算每个线程处理的行数，因为不一定能够整除，所以要分配一个线程多做或者少做点
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String sepNum = decimalFormat.format((float)totalNum/threadNum);
            //这里就看个人策略了，是留最后一个线程少干一些，还是最后一个多干一些，本人选择少干一些
            System.identityHashCode(threadNum);
        }catch (Exception ec){
            ec.printStackTrace();
        }finally {
            fileInputStream.close();
        }

    }
}
