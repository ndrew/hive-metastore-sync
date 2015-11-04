# hive-metastore-sync
Replicate hive metastore from the cluster to another one

## How to build?
Run maven as following:
```mvn clean package```

## How to test?
* Create two single-node clusters with hadoop and hive.
* Expected host names: box1 & box2
* Run the tests:

```
cd tests
./run.sh
```

## Creating test boxes with vagrant-lxc

To simplify the testing process, there is a vagrant-lxc template which could be used to create box1 and box2.
You have to install vagrant and vagrant-lxc plugin to use it. Then run the following commands:

```
cd vagrant/lxc
vagrant up
```

This command creates and runs two containers: box1 and box2, both provisioned with hadoop and hive.
To setup a password-less ssh access:

```
ssh-copy-id vagrantbox1
ssh-copy-id vagrant@box2
```

To remove created containers use ```destroy```:

```
vagrant destroy
```
