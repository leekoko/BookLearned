## Day2 方法迁移（Move Method）

D：原来代码

```java
public class BankAccount{
	public BankAccount(int accountAge, int creditScore, AccountInterest accountInterest){
	    AccountAge = accountAge;
	    CreditScore = creditScore;
	    AccountInterest = accountInterest;
	}
    private int AccountAge;
    private int CreditScore;
    public AccountInterest AccountInterest;
    
	public double CalculateInterestRate()
    {
        if (CreditScore > 800)
            return 0.02;

        if (AccountAge > 10)
            return 0.03;

        return 0.05;
    }
	

    public int getAccountAge() {
		return AccountAge;
	}

	public void setAccountAge(int accountAge) {
		AccountAge = accountAge;
	}

	public int getCreditScore() {
		return CreditScore;
	}

	public void setCreditScore(int creditScore) {
		CreditScore = creditScore;
	}

	public AccountInterest getAccountInterest() {
		return AccountInterest;
	}

	public void setAccountInterest(AccountInterest accountInterest) {
		AccountInterest = accountInterest;
	}
}

class AccountInterest{
	 
	private BankAccount Account;
	
	public double InterestRate(){
	    return Account.CalculateInterestRate(); 
	}
	
	public boolean IntroductoryRate(){
	    return Account.CalculateInterestRate() < 0.05; 
	}

	public BankAccount getAccount() {
		return Account;
	}
	
	public void setAccount(BankAccount account) {
		Account = account;
	}
	
	public AccountInterest(BankAccount account)
	{
	    Account = account;
	}

}
```

M：这段代码有什么问题？

Z：``CalculateInterestRate（）``方法在AccountInterest类中使用，但是没在BankAccount中

M：

```java
public class BankAccount{
	public BankAccount(int accountAge, int creditScore, AccountInterest accountInterest){
	    AccountAge = accountAge;
	    CreditScore = creditScore;
	    AccountInterest = accountInterest;
	}
    public int AccountAge;
    public int CreditScore;
    public AccountInterest AccountInterest;
    

    public int getAccountAge() {
		return AccountAge;
	}

	public void setAccountAge(int accountAge) {
		AccountAge = accountAge;
	}

	public int getCreditScore() {
		return CreditScore;
	}

	public void setCreditScore(int creditScore) {
		CreditScore = creditScore;
	}

	public AccountInterest getAccountInterest() {
		return AccountInterest;
	}

	public void setAccountInterest(AccountInterest accountInterest) {
		AccountInterest = accountInterest;
	}
}

class AccountInterest{
	 
	private BankAccount Account;
	
	public double InterestRate(){
	    return CalculateInterestRate(); 
	}
	
	public boolean IntroductoryRate(){
	    return CalculateInterestRate() < 0.05; 
	}
	
	public double CalculateInterestRate()
    {
        if (Account.CreditScore > 800)
            return 0.02;

        if (Account.AccountAge > 10)
            return 0.03;

        return 0.05;
    }
	
	
	public BankAccount getAccount() {
		return Account;
	}
	
	public void setAccount(BankAccount account) {
		Account = account;
	}
	
	public AccountInterest(BankAccount account)
	{
	    Account = account;
	}

}
```





















































