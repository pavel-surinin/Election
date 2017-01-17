var AdminProductComponent = React.createClass({

  render: function() {
    return (
      <div className="container vertical-center">
        <div className="row">
          <div className="col-md-4 col-md-offset-4">
            <div className="login-panel panel panel-default">
              <div className="panel-body">
                <form onSubmit={this.props.onHandleUpdate} role="form">
                  <fieldset>
                  <div className="input-group">
                    <span className="input-group-addon" id="basic-addon1">Author</span>
                    <input
                      type="text"
                      onChange={this.props.onHandleChangeAuthor}
                      value={this.props.author}
                      className="form-control"
                      placeholder="Author"
                      name="author"
                      required
                    />
                  </div>
                  <br/>
                  <div className="input-group">
                    <span className="input-group-addon" id="basic-addon1">Title</span>
                      <input
                        type="text"
                        onChange={this.props.onHandleChangeTitle}
                        value={this.props.title}
                        className="form-control"
                        placeholder="Title"
                        name="title"
                        required
                      />
                    </div>
                    <br/>
                    <div className="input-group">
                      <span className="input-group-addon" id="basic-addon1">ISBN</span>
                        <input
                          type="text"
                          onChange={this.props.onHandleChangeIsbn}
                          value={this.props.isbn}
                          className="form-control"
                          placeholder="ISBN"
                          name="isbn"
                          required
                        />
                      </div>
                      <br/>
                      <div className="input-group">
                        <span className="input-group-addon" id="basic-addon1">Published at {moment(this.props.publishedAt).format('L')}</span>
                          <input
                            type="text"
                            onChange={this.props.onHandleChangePublishedAt}
                            value={this.props.publishedAt}
                            className="form-control"
                            placeholder="Published At"
                            name="PublishedAt"
                            required
                          />
                        </div>
                        <br/>
                        <div className="input-group">
                          <span className="input-group-addon" id="basic-addon1">Quantity</span>
                            <input
                              type="text"
                              onChange={this.props.onHandleChangeQuantity}
                              value={this.props.quantity}
                              className="form-control"
                              placeholder="Quantity"
                              name="quantity"
                              required
                            />
                          </div>
                          <br/>
                    <button className="btn btn-lg btn-success btn-block">
                      {this.props.submitButtonName}
                    </button>
                  </fieldset>
                </form>
                <button onClick={this.props.onHandleCancel} className="btn btn-lg btn-warning btn-block">
                Cancel
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

});


window.AdminProductComponent = AdminProductComponent;
