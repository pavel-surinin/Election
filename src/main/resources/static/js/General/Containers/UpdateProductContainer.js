var UpdateProductContainer = React.createClass({
  getInitialState: function() {
    return {
      details : [],
      author : '',
      title: '',
      isbn: '',
      publishedAt: '',
      quantity: '',
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
    .get('/api/books/' + this.props.params.id)
    .then(function(response){
      self.setState({
        id : response.data.id,
        author: response.data.author,
        title: response.data.title,
        isbn: response.data.isbn,
        publishedAt: response.data.publishedAt,
        quantity: response.data.quantity,
      });
    })
    .catch(function(err){
      console.error('Axios get/api/book:id error - ', err);
    });
  },

  onHandleChangeAuthor : function(event){
    this.setState({author: event.target.value});
  },

  onHandleChangeTitle : function(event){
    this.setState({title: event.target.value});
  },

  onHandleChangeIsbn : function(event){
    this.setState({isbn: event.target.value});
  },

  onHandleChangePublishedAt : function(event){
    this.setState({publishedAt : event.target.value})
  },

  onHandleChangeQuantity : function(event){
    this.setState({quantity: event.target.value});
  },

  onHandleUpdate: function(event) {
    var self = this;
    axios.put('/api/books/' + self.state.id,
      {
        id : self.state.id,
        author: self.state.author,
        title: self.state.title,
        isbn: self.state.isbn,
        publishedAt: self.state.publishedAt,
        quantity: self.state.quantity,})
    .then(function(response){
      console.log(response.data);
      self.context.router.push('/');
    })
    .catch(function(err){
      console.error('Update failed at UpdateProductContainer - ', err);
    });
    event.preventDefault();
  },
  onHandleCancel:function(){
    this.context.router.push('/');
  },

  render: function() {
    return (
      <AdminProductComponent
      submitButtonName='Update'
      
      onHandleChangeTitle={this.onHandleChangeTitle}
      onHandleChangeAuthor={this.onHandleChangeAuthor}
      onHandleChangeIsbn={this.onHandleChangeIsbn}
      onHandleChangePublishedAt={this.onHandleChangePublishedAt}
      onHandleChangeQuantity={this.onHandleChangeQuantity}

      onHandleUpdate={this.onHandleUpdate}
      onHandleCancel={this.onHandleCancel}

      author={this.state.author}
      title={this.state.title}
      isbn={this.state.isbn}
      publishedAt={this.state.publishedAt}
      quantity={this.state.quantity}

      details={this.state.details}/>

    );
  }
});

UpdateProductContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.UpdateProductContainer = UpdateProductContainer;
