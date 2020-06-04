package com.zzd.spring.parent.service.blockchain.controller;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.alibaba.fastjson.JSONObject;
import com.zzd.spring.parent.service.blockchain.EviContract;
import com.zzd.spring.parent.service.blockchain.config.ConfigProperties;
import com.zzd.spring.parent.service.blockchain.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import springfox.documentation.annotations.ApiIgnore;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.List;

import static com.zzd.spring.parent.service.blockchain.EviContract.FUNC_GETDATA;

/**
 * @Description 说明类的用途
 * @ClassName EthController
 * @Author zzd
 * @Date 2019/12/10 19:56
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/blockchain")
@Api(value = "区块链相关接口-2", protocols = "http/https", tags = "区块链相关接口-2【ZZD】")
public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    @Autowired
    private Web3j web3j;
    @Autowired
    private Admin admin;
    @Autowired
    private ConfigProperties configProperties;
//    public static final BigInteger GAS_LIMIT = DefaultGasProvider.GAS_LIMIT;
//    public static final BigInteger GAS_PRICE = DefaultGasProvider.GAS_PRICE;

    public static final BigInteger GAS_LIMIT = Contract.GAS_LIMIT;//BigInteger.valueOf(999_999_999);//
    public static final BigInteger GAS_PRICE = Contract.GAS_PRICE;//BigInteger.valueOf(999_999_999);//

    /**
     * 查询区块高度
     *
     * @return
     */
    @ApiOperation(value = "查询区块高度", notes = "查询区块高度【zzd】")
    @RequestMapping(value = "/height", method = RequestMethod.POST)
    public Long getHeight() {
        EthBlockNumber blockNumber = null;
        try {
            blockNumber = web3j.ethBlockNumber().send();
            long height = blockNumber.getBlockNumber().longValue();
            return height;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 某一个区块的交易数量
     *
     * @return
     */
    @ApiOperation(value = "某一个区块的交易数量", notes = "某一个区块的交易数量【zzd】")
    @RequestMapping(value = "/blockCount", method = RequestMethod.POST)
    public Long getBlockCountByNumber(@ApiParam(value = "区块编号", name = "number") @RequestParam String number) {
        try {
            DefaultBlockParameter parameter = DefaultBlockParameter.valueOf(new BigInteger(number));
            EthGetBlockTransactionCountByNumber send = web3j.ethGetBlockTransactionCountByNumber(parameter).send();
            BigInteger transactionCount = send.getTransactionCount();
            return transactionCount.longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户的Nounce
     *
     * @param address
     * @return
     */
    @ApiOperation(value = "获取用户的Nounce", notes = "获取用户的Nounce【zzd】")
    @RequestMapping(value = "/nounce", method = RequestMethod.POST)
    public BigInteger getNounce(@ApiParam(value = "用户地址，账户", name = "address") @RequestParam String address) {
        try {
            EthGetTransactionCount getNonce = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            if (getNonce != null) {
                return getNonce.getTransactionCount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户的余额
     *
     * @return
     */
    @ApiOperation(value = "查询用户的余额", notes = "查询用户的余额【zzd】")
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public BigInteger getBalance(@ApiParam(value = "用户地址，账户", name = "address") @RequestParam String address) {
        EthGetBalance balance = null;
        try {
            balance = web3j.ethGetBalance(address, DefaultBlockParameterName.PENDING).send();
            if (null != balance) {
                return balance.getBalance();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交易
     *
     * @param fromAddr
     * @param toAddr
     * @param privateKey
     * @param amount
     * @param data
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transferETH(@ApiParam(value = "出钱方账户", name = "fromAddr") @RequestParam String fromAddr,
                              @ApiParam(value = "接收方账户", name = "toAddr") @RequestParam String toAddr,
                              @ApiParam(value = "密码", name = "privateKey") @RequestParam String privateKey,
                              @ApiParam(value = "交易数量", name = "amount") @RequestParam BigDecimal amount,
                              @ApiParam(value = "数据", name = "data") @RequestParam String data) {
        // 获得nonce
        BigInteger nonce = getNounce(fromAddr);
        // value 转换
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        BigInteger gasPrice = GAS_PRICE;
        // 计算gasLimit
        BigInteger gasLimit = GAS_LIMIT;
        // 查询调用者余额，检测余额是否充足
        BigDecimal ethBalance = new BigDecimal(getBalance(fromAddr));
        BigDecimal balance = Convert.toWei(ethBalance, Convert.Unit.ETHER);
        // balance < amount   gasLimit ??
        if (balance.compareTo(new BigDecimal(gasPrice.multiply(gasLimit).add(value))) < 0) {
            throw new RuntimeException("余额不足，请核实");
        }
        return signAndSend(nonce, gasPrice, gasLimit, toAddr, value, data, privateKey);
    }

    @ApiOperation(value = "存入数据（也就是交易），数据存入data", notes = "存入数据（也就是交易）【zzd】")
    @RequestMapping(value = "/transfer2", method = RequestMethod.POST)
    public String transferETH2(@ApiParam(value = "出钱方账户", name = "fromAddr") @RequestParam String fromAddr,
                               @ApiParam(value = "账户", name = "toAddr") @RequestParam String toAddr,
                               @ApiParam(value = "密码", name = "privateKey") @RequestParam String privateKey,
                               @ApiParam(value = "交易数量", name = "amount") @RequestParam BigDecimal amount,
                               @ApiParam(value = "数据", name = "data") @RequestParam String data) throws IOException {
        PersonalUnlockAccount unlock = admin.personalUnlockAccount(fromAddr, privateKey).send();
        if (!unlock.accountUnlocked()) {
            return "not unlock";
        }
        // 获得nonce
        BigInteger nonce = getNounce(fromAddr);
        // value 转换
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        BigInteger gasPrice = GAS_PRICE;
        // 计算gasLimit
        BigInteger gasLimit = GAS_LIMIT;
        // 查询调用者余额，检测余额是否充足
        BigDecimal ethBalance = new BigDecimal(getBalance(fromAddr));
        BigDecimal balance = Convert.toWei(ethBalance, Convert.Unit.ETHER);
        // balance < amount   gasLimit ??
        if (balance.compareTo(new BigDecimal(gasPrice.multiply(gasLimit).add(value))) < 0) {
            throw new RuntimeException("余额不足，请核实");
        }
        String encode = Base64Encoder.encode(data.getBytes("utf-8"));
        String dataHexed = stringToHexString(encode);
        System.out.println(dataHexed);
        Transaction transaction = Transaction.createFunctionCallTransaction(fromAddr, nonce, gasPrice, gasLimit, toAddr, value, dataHexed);
        EthSendTransaction send = web3j.ethSendTransaction(transaction).send();
        System.out.println(JSONObject.toJSONString(send));
        return send.getTransactionHash();
    }

    /**
     * 查询交易信息，通过交易hash
     *
     * @param transHash
     * @return
     */
    @ApiOperation(value = "查询数据，也就是查询存入交易中的Data", notes = "查询数据，【zzd】")
    @RequestMapping(value = "/getTransfer", method = RequestMethod.POST)
    public AjaxResult<String> getTransfer(@RequestBody  QueryTradeDTO param) {

        String result = "";
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            EthTransaction send = web3j.ethGetTransactionByHash(param.getTransHash()).send();
            String input = send.getResult().getInput();
            String ouot = hexStringToString(input);
            System.out.println(ouot);
            int start = ouot.indexOf("{");
            int end = ouot.lastIndexOf("}")+1;
            result = ouot.substring(start,end);
            String Sendresult = JSONObject.toJSONString(send.getResult());
            ajaxResult = AjaxResultUtil.getTrueAjaxResult(ajaxResult);
            ajaxResult.setData(result);
            System.out.println(Sendresult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }

    /**
     * 部署java的智能合约
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "部署合约", notes = "部署合约，返回合约地址【zzd】")
    @RequestMapping(value = "/deployContract", method = RequestMethod.POST)
    public AjaxResult<String> deployContract() throws Exception {
        String path = configProperties.getKeyFilePath() + configProperties.getKeyFileName();
        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
        EviContract send = EviContract.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
        String contractAddress = send.getContractAddress();
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        ajaxResult = AjaxResultUtil.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(contractAddress);
        return ajaxResult;
    }

    @ApiOperation(value = "创建账户", notes = "创建账户,返回账户地址账号，【zzd】")
    @RequestMapping(value = "/newWallet", method = RequestMethod.POST)
    public String newWallet(@ApiParam(value = "密码", name = "password") @RequestParam String password) throws IOException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        String s = WalletUtils.generateNewWalletFile(password, new File(configProperties.getKeyFilePath()));
        return s;
    }

    @ApiOperation(value = "查询所有账户信息", notes = "查询所有账户信息，【zzd】")
    @RequestMapping(value = "/getAccounts", method = RequestMethod.POST)
    public List<String> getAccounts() throws IOException {
        EthAccounts send = web3j.ethAccounts().send();
        return send.getAccounts();
    }

    @ApiOperation(value = "合约中的数据保存", notes = "合约中的数据保存，返回交易hash【zzd】")
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    public String saveData(@RequestBody SaveDataDTO param) throws Exception {
        String path = configProperties.getKeyFilePath() + configProperties.getKeyFileName();
        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
        EviContract contract = EviContract.load(param.getContractAddress(), web3j, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.setData(param.getKey().getBytes("utf-8"), param.getContent()).send();
        LOGGER.info(receipt.toString());
        String transactionHash = receipt.getTransactionHash();
        return transactionHash;
    }

    @ApiOperation(value = "合约中的数据保存", notes = "合约中的数据保存，返回交易hash【zzd】")
    @RequestMapping(value = "/saveData2", method = RequestMethod.POST)
    public AjaxResult<String> saveData2( HttpServletRequest request
//            @RequestParam(value="file") MultipartFile file,
//                                        @RequestParam(value = "contractAddress") String contractAddress,
//                                        @RequestParam(value = "key") String key
    ) throws Exception {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile file = params.getFile("file");
        String contractAddress = params.getParameter("contractAddress");
        String key = params.getParameter("key");

        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(file.getBytes()).replaceAll("\r\n", "");
        String path = configProperties.getKeyFilePath() + configProperties.getKeyFileName();
        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
        EviContract contract = EviContract.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);

        ChainContent chainContent = new ChainContent();
        chainContent.setContent(encode);
        TransactionReceipt receipt = contract.setData(key.getBytes("utf-8"), JSONObject.toJSONString(chainContent)).send();
        LOGGER.info(receipt.toString());
        String transactionHash = receipt.getTransactionHash();
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        ajaxResult = AjaxResultUtil.getTrueAjaxResult(ajaxResult);
        ajaxResult.setData(transactionHash);
        return ajaxResult;
    }

    @ApiOperation(value = "合约中的数据获取", notes = "合约中的数据获取，【zzd】")
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public String getData(@ApiParam(value = "合约地址", name = "contractAddress") @RequestParam String contractAddress,
                          @ApiParam(value = "保存的key值", name = "key") @RequestParam String key) throws Exception {
        String result = "";
//        String path = configProperties.getKeyFilePath()+configProperties.getKeyFileName();
//        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
//        EviContract contract = EviContract.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
//        TransactionReceipt send = contract.getData(key.getBytes("utf-8")).send();
        Function function = new Function(
                FUNC_GETDATA,
                Arrays.asList(EviContract.stringToBytes32(key.getBytes())),
                Arrays.asList(new TypeReference<EviContract.DataSavedEventResponse>() {
                }));
        List<Type> inputParameters = function.getInputParameters();
        result = inputParameters.toArray().toString();
        String s = JSONObject.toJSONString(result);
        return s;
    }

    @ApiOperation(value = "合约中的数据获取2", notes = "合约中的数据获取2，【zzd】")
    @RequestMapping(value = "/getData2", method = RequestMethod.POST)
    public String getData2(@ApiParam(value = "合约地址", name = "contractAddress") @RequestParam String contractAddress,
                           @ApiParam(value = "保存的key值", name = "key") @RequestParam String key) throws Exception {
        String path = configProperties.getKeyFilePath() + configProperties.getKeyFileName();
        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
        EviContract contract = EviContract.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);

        TransactionReceipt receipt = contract.getData(key.getBytes("utf-8")).send();
        List<EviContract.DataSavedEventResponse> dataSavedEvents = contract.getDataSavedEvents(receipt);
        String s = JSONObject.toJSONString(dataSavedEvents);
        return s;
    }

    @ApiIgnore
    @RequestMapping(value = "/decodeData")
    public String decodeData(String contractAddress, String key) throws Exception {
        String path = configProperties.getKeyFilePath() + configProperties.getKeyFileName();
        Credentials credentials = WalletUtils.loadCredentials(configProperties.getPassword(), path);
        EviContract contract = EviContract.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.getData(key.getBytes("utf-8")).send();
        return receipt.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] bytes = toBytes("419ce0");
        String ss = new String(bytes, "UTF-8");
        System.out.print(ss);
    }

    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 交易
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param data
     * @param privateKey
     * @return
     */
    private String signAndSend(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, String privateKey) {
        String txHash = "";
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }

        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        Credentials credentials = Credentials.create(ecKeyPair);
        LOGGER.info(credentials.getAddress());
        byte[] signMessage;
        signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String signData = Numeric.toHexString(signMessage);
        if (!"".equals(signData)) {
            try {
                EthSendTransaction send = web3j.ethSendRawTransaction(signData).send();
                txHash = send.getTransactionHash();
                System.out.println(JSONObject.toJSONString(send));
            } catch (IOException e) {
                throw new RuntimeException("交易异常");
            }
        }
        return txHash;
    }

    public BlockchainTransaction process(BlockchainTransaction trx) throws IOException {

        EthAccounts accounts = web3j.ethAccounts().send();
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(accounts.getAccounts().get(trx.getFromId()),
                DefaultBlockParameterName.LATEST).send();

        Transaction transaction = Transaction.createEtherTransaction(
                accounts.getAccounts().get(trx.getFromId()), transactionCount.getTransactionCount(), BigInteger.valueOf(trx.getValue()),
                BigInteger.valueOf(21_000), accounts.getAccounts().get(trx.getToId()), BigInteger.valueOf(trx.getValue()));

        EthSendTransaction response = web3j.ethSendTransaction(transaction).send();

        if (response.getError() != null) {
            trx.setAccepted(false);
            LOGGER.info("Tx rejected: {}", response.getError().getMessage());
            return trx;
        }
        trx.setAccepted(true);
        String txHash = response.getTransactionHash();
        LOGGER.info("Tx hash: {}", txHash);
        trx.setId(txHash);
        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(txHash).send();
        receipt.getTransactionReceipt().ifPresent(transactionReceipt -> LOGGER.info("Tx receipt:  {}",
                transactionReceipt.getCumulativeGasUsed().intValue()));
        return trx;

    }

    public void listen() {
        web3j.transactionFlowable().subscribe(tx -> {
            LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());
            try {
                EthCoinbase coinbase = web3j.ethCoinbase().send();
                EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(tx.getFrom(), DefaultBlockParameterName.LATEST).send();
                LOGGER.info("Tx count: {}", transactionCount.getTransactionCount().intValue());
                if (transactionCount.getTransactionCount().intValue() % 10 == 0) {
                    EthGetTransactionCount tc = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
                    Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), tc.getTransactionCount(), tx.getValue(), BigInteger.valueOf(21_000), tx.getFrom(), tx.getValue());
                    web3j.ethSendTransaction(transaction).send();
                }
            } catch (IOException e) {
                LOGGER.error("Error getting transactions", e);
            }
        });
//        web3j.transactionObservable().subscribe(tx -> {
//            LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());
//            try {
//                EthCoinbase coinbase = web3j.ethCoinbase().send();
//                EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(tx.getFrom(), DefaultBlockParameterName.LATEST).send();
//                LOGGER.info("Tx count: {}", transactionCount.getTransactionCount().intValue());
//                if (transactionCount.getTransactionCount().intValue() % 10 == 0) {
//                    EthGetTransactionCount tc = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
//                    Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), tc.getTransactionCount(), tx.getValue(), BigInteger.valueOf(21_000), tx.getFrom(), tx.getValue());
//                    web3j.ethSendTransaction(transaction).send();
//                }
//            } catch (IOException e) {
//                LOGGER.error("Error getting transactions", e);
//            }
//        });
        LOGGER.info("Subscribed");
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s.startsWith("0x")) {
            s = s.replaceFirst("0x", "");
        }
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
//            s = new String(baKeyword, "gbk");
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    /**
     * byte数组转16进制字符串
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
