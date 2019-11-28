import React, { useState, useEffect } from 'react';
import { format } from 'date-fns';
import {
  Form,
  Input,
  InputNumber,
  Button,
  Table,
  Divider,
  Modal,
  Spin,
  message
} from 'antd';
import './App.css';
import { FormComponentProps } from 'antd/lib/form';

const { confirm } = Modal;

interface ActionProps extends FormComponentProps {
  text: any;
  record: any;
  data: Data[];
  setData: Function;
}

interface CreateForm extends FormComponentProps {
  createing: boolean;
  setCreateing: Function;
  data: Data[];
  setData: Function;
  reloading: boolean;
  setReloading: Function;
  fetchData: Function;
}

const Action = Form.create<ActionProps>()((props: ActionProps) => {
  const { data, setData } = props;
  const { getFieldDecorator, getFieldValue, getFieldsValue } = props.form;
  const [visible, setVisible] = useState(false);
  const [editing, setEditing] = useState(false);
  const handleEdit = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    e.preventDefault();
    setVisible(true);
  };
  const handleDelete = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    e.preventDefault();
    confirm({
      title: 'Do you Want to delete these items?',
      okText: 'Yes',
      okType: 'danger',
      cancelText: 'No',
      onOk() {
        return fetch(`/admin/allocs/delete?bizTag=${props.record.bizTag}`, {
          method: 'POST'
        })
          .then(resp => resp.json())
          .then(resp => {
            if (resp.status === 200) {
              data.forEach((item, i) => {
                if (item.bizTag === props.record.bizTag) {
                  data.splice(i, 1);
                }
              });
              setData(Object.assign([], data));
              message.success(resp.message);
            } else {
              message.error(resp.message);
            }
          });
      },
      onCancel() {}
    });
  };
  const handleOk = () => {
    setEditing(true);
    const body = Object.assign(props.record, getFieldsValue());
    fetch('/admin/allocs/update', {
      method: 'POST',
      body: JSON.stringify(body),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
      .then(resp => resp.json())
      .then(resp => {
        if (resp.status === 200) {
          const { bizTag } = props.record;
          for (let i = 0; i < props.data.length; i++) {
            const item = props.data[i];
            if (item.bizTag === bizTag) {
              item.description = getFieldValue('description');
              item.updateTime = format(new Date(), 'yyyy-MM-dd HH:mm:ss');
              break;
            }
          }
          setData(Object.assign([], data));
          setVisible(false);
          message.success(resp.message);
        } else {
          message.error(resp.message);
        }
        setEditing(false);
      });
  };
  const handleCancel = () => {
    setVisible(false);
  };
  return (
    <span>
      <a onClick={handleEdit}>Edit</a>
      <Divider type="vertical" />
      <a onClick={handleDelete}>Delete</a>
      <Modal
        title="Edit"
        visible={visible}
        onOk={handleOk}
        onCancel={handleCancel}
        okButtonProps={{ loading: editing }}
      >
        <Form
          labelCol={{ span: 5 }}
          wrapperCol={{ span: 16 }}
          onSubmit={e => e.preventDefault()}
        >
          <Form.Item label="BizTag">
            <Input disabled={true} value={props.record.bizTag} />
          </Form.Item>
          <Form.Item label="MaxID">
            <Input disabled={true} value={props.record.maxId} />
          </Form.Item>
          <Form.Item label="Step">
            <Input disabled={true} value={props.record.step} />
          </Form.Item>
          <Form.Item label="Description">
            {getFieldDecorator('description', {
              initialValue: props.record.description
            })(<Input placeholder="Description" />)}
          </Form.Item>
          <Form.Item label="UpateTime">
            <Input disabled={true} value={props.record.updateTime} />
          </Form.Item>
        </Form>
      </Modal>
    </span>
  );
});

const CreateForm = Form.create<CreateForm>()((props: CreateForm) => {
  const {
    createing,
    setCreateing,
    data,
    setData,
    reloading,
    setReloading,
    fetchData
  } = props;
  const { getFieldDecorator, validateFields } = props.form;

  const handleReload = (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();
    setReloading(true);
    setTimeout(() => {
      fetchData().then(() => setReloading(false));
    }, 500);
  };
  const handleSubmit = (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();
    validateFields((err, values) => {
      if (!err) {
        console.log(values);
        setCreateing(true);
        fetch('/admin/allocs/create', {
          method: 'POST',
          body: JSON.stringify(values),
          headers: new Headers({
            'Content-Type': 'application/json'
          })
        })
          .then(resp => resp.json())
          .catch(err => console.log('Error:', err))
          .then(resp => {
            console.log('Success:', resp);
            if (resp.status === 200) {
              values.key = values.bizTag;
              values.updateTime = format(new Date(), 'yyyy-MM-dd HH:mm:ss');
              data.push(values);
              setData(Object.assign([], data));
              message.success(resp.message);
            } else {
              message.error(resp.message);
            }
            setCreateing(false);
          });
      }
    });
  };
  return (
    <Form
      style={{ marginBottom: '0.5em' }}
      layout="inline"
      onSubmit={e => e.preventDefault()}
    >
      <Form.Item>
        {getFieldDecorator('bizTag', {
          rules: [{ required: true, message: 'Please input BizTag' }]
        })(<Input style={{ width: '200px' }} placeholder="BizTag" />)}
      </Form.Item>
      <Form.Item>
        {getFieldDecorator('maxId', {
          initialValue: 1,
          rules: [{ required: true, message: 'Please input MaxID' }]
        })(
          <InputNumber style={{ width: '200px' }} min={1} placeholder="MaxID" />
        )}
      </Form.Item>
      <Form.Item>
        {getFieldDecorator('step', {
          initialValue: 2000,
          rules: [{ required: true, message: 'Please input Step' }]
        })(
          <InputNumber style={{ width: '200px' }} min={1} placeholder="Step" />
        )}
      </Form.Item>
      <Form.Item>
        {getFieldDecorator('description')(
          <Input style={{ width: '200px' }} placeholder="Description" />
        )}
      </Form.Item>
      <Form.Item>
        <Button
          type="primary"
          loading={createing}
          onClick={handleSubmit}
          htmlType="submit"
        >
          {createing ? 'Createing...' : 'Create'}
        </Button>
      </Form.Item>
      <Form.Item>
        <Button
          type="default"
          htmlType="button"
          loading={reloading}
          onClick={handleReload}
        >
          {reloading ? 'Reloading...' : 'Reload'}
        </Button>
      </Form.Item>
    </Form>
  );
});

interface Data {
  key: string;
  bizTag: string;
  maxId: number;
  step: number;
  description: string | null | undefined;
  updateTime: string;
}

export default () => {
  const [reloading, setReloading] = useState(false);
  const [createing, setCreateing] = useState(false);
  const [data, setData] = useState(new Array<Data>());
  const columns = [
    {
      title: 'BizTag',
      dataIndex: 'bizTag',
      key: 'bizTag'
    },
    {
      title: 'MaxID',
      dataIndex: 'maxId',
      key: 'maxId'
    },
    {
      title: 'Step',
      dataIndex: 'step',
      key: 'step'
    },
    {
      title: 'Description',
      key: 'description',
      dataIndex: 'description'
    },
    {
      title: 'UpdateTime',
      key: 'updateTime',
      dataIndex: 'updateTime'
    },
    {
      title: 'Action',
      key: 'action',
      render: (text: any, record: any) => (
        <Action text={text} record={record} data={data} setData={setData} />
      )
    }
  ];
  const fetchData = () =>
    fetch('/admin/allocs')
      .then(resp => resp.json())
      .then(resp => {
        if (resp.status === 200) {
          if (resp.data && resp.data.length > 0) {
            resp.data.map((item: Data) => {
              item.key = item.bizTag;
            });
          }
          setData(resp.data);
        }
      });

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <CreateForm
        createing={createing}
        setCreateing={setCreateing}
        data={data}
        setData={setData}
        reloading={reloading}
        setReloading={setReloading}
        fetchData={fetchData}
      />
      <Spin tip="Loading..." delay={100} spinning={reloading}>
        <Table columns={columns} dataSource={data} pagination={false} />
      </Spin>
    </div>
  );
};
