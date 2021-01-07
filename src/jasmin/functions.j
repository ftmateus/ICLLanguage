.source functions.j
.class public functions
.super java/lang/Object
	
.method public static printI(I)V
	.limit stack 2
	getstatic java/lang/System/out Ljava/io/PrintStream;
	iload_0
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return
.end method
	
.method public static printO(Ljava/lang/Object;)V
	.limit stack 2
	getstatic java/lang/System/out Ljava/io/PrintStream;
	aload_0
	invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return
.end method