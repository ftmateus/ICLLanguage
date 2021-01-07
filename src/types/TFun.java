package types;

import java.util.ArrayList;
import java.util.List;

public class TFun implements IType {
	
	private List<IType> types;
	private IType rType;
	
	public TFun(IType rType) {
		this.rType = rType;
		types = new ArrayList<>();
	}
	
	public void addType(IType t) {
		types.add(t);
	}
	
	public List<IType> getTypes() {
		return types;
	}
	
	public IType getReturnType() {
		return rType;
	}
	

}
