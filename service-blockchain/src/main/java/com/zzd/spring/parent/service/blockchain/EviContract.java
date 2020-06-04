package com.zzd.spring.parent.service.blockchain;


import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class EviContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610573806100206000396000f3fe6080604052600436106100345760003560e01c80632d3e65361461003957806354f6127f146100e8578063aebac33a146101b8575b600080fd5b6100e66004803603604081101561004f57600080fd5b8135919081019060408101602082013564010000000081111561007157600080fd5b82018360208201111561008357600080fd5b803590602001918460018302840111640100000000831117156100a557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506101eb945050505050565b005b3480156100f457600080fd5b506101126004803603602081101561010b57600080fd5b503561030a565b60405180868152602001856001600160a01b03166001600160a01b0316815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610179578181015183820152602001610161565b50505050905090810190601f1680156101a65780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c457600080fd5b50610112600480360360208110156101db57600080fd5b50356001600160a01b03166103e3565b60008281526001602081815260409092204280825581830180546001600160a01b0319163390811790915560028301849055600383018790558551919490939261023d926004909101918701906104a2565b50816001600160a01b0316336001600160a01b03167f7aab6b24a38e9a13e819bd65a5fbf036d0897aa5985fc5878474d9e79d65ab8e858489896040518085815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156102c65781810151838201526020016102ae565b50505050905090810190601f1680156102f35780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a35050505050565b600081815260016020818152604080842080548185015460028084015460038501546004909501805487516101009a8216159a909a02600019011692909204601f81018890048802890188019096528588528897889788976060976001600160a01b0390961695939492918391908301828280156103c95780601f1061039e576101008083540402835291602001916103c9565b820191906000526020600020905b8154815290600101906020018083116103ac57829003601f168201915b505050505090509450945094509450945091939590929450565b600060208181529181526040908190208054600180830154600280850154600386015460048701805489516101009782161597909702600019011693909304601f81018a90048a0286018a0190985287855294976001600160a01b0390931696909593928301828280156104985780601f1061046d57610100808354040283529160200191610498565b820191906000526020600020905b81548152906001019060200180831161047b57829003601f168201915b5050505050905085565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104e357805160ff1916838001178555610510565b82800160010185558215610510579182015b828111156105105782518255916020019190600101906104f5565b5061051c929150610520565b5090565b61053a91905b8082111561051c5760008155600101610526565b9056fea2646970667358221220fe9911d6e1929b1ecf2ea3a342017d3e8eef4f396d8e2cb1c51bd6f4c08ebd4c64736f6c63430006000033";

    public static final String FUNC_EVIDENCE = "evidence" ;

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_SETDATA = "setData";

    public static final Event DATASAVED_EVENT = new Event("DataSaved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected EviContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EviContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EviContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EviContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DataSavedEventResponse> getDataSavedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DATASAVED_EVENT, transactionReceipt);
        ArrayList<DataSavedEventResponse> responses = new ArrayList<DataSavedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DataSavedEventResponse typedResponse = new DataSavedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._handler = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.content = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DataSavedEventResponse> dataSavedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DataSavedEventResponse>() {
            @Override
            public DataSavedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DATASAVED_EVENT, log);
                DataSavedEventResponse typedResponse = new DataSavedEventResponse();
                typedResponse.log = log;
                typedResponse._handler = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.content = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DataSavedEventResponse> dataSavedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DATASAVED_EVENT));
        return dataSavedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> evidence(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EVIDENCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> getData(byte[] _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETDATA, 
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_key)),
                Arrays.<Type>asList(stringToBytes32(_key)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setData(byte[] _key, String content) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETDATA, 
//                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_key),
                Arrays.<Type>asList(stringToBytes32(_key),
                new org.web3j.abi.datatypes.Utf8String(content)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
    public static Bytes32 stringToBytes32(byte[] _key) {
        byte[] byteValue = _key;
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }
    public static Utf8String stringToUtf8String(String str) {
        Utf8String s=new Utf8String(str);
        return s;
    }

    @Deprecated
    public static EviContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EviContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EviContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EviContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EviContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EviContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EviContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EviContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EviContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EviContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<EviContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EviContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EviContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EviContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EviContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EviContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DataSavedEventResponse extends BaseEventResponse implements Type{
        public String _handler;

        public String sender;

        public BigInteger timestamp;

        public BigInteger version;

        public byte[] hash;

        public String content;

        @Override
        public Object getValue() {
            return content;
        }

        @Override
        public String getTypeAsString() {
            return "{" +
                    "_handler='" + _handler + '\'' +
                    ", sender='" + sender + '\'' +
                    ", timestamp=" + timestamp +
                    ", version=" + version +
                    ", hash=" + Arrays.toString(hash) +
                    ", content='" + content + '\'' +
                    '}';
        }

        @Override
        public String toString() {
            return "DataSavedEventResponse{" +
                    "_handler='" + _handler + '\'' +
                    ", sender='" + sender + '\'' +
                    ", timestamp=" + timestamp +
                    ", version=" + version +
                    ", hash=" + Arrays.toString(hash) +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
